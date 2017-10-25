package guru.springframework.bootstrap;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import guru.springframework.domain.Category;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {
	
	private RecipeRepository recipeRepository;
	private CategoryRepository categoryRepository;
	private UnitOfMeasureRepository unitOfMeasureRepository;
	
	public DevBootstrap(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
		this.recipeRepository = recipeRepository;
		this.categoryRepository = categoryRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.debug("Loading Bootstrap data");
		
		// TODO Auto-generated method stub
		initDb();
	}
	
	private Set<Category> generateCategories(String... categories) {
		return Arrays.asList( categories )
			.stream()
			.map(s -> { Category c = new Category(); c.setDescription(s); return c; } )
			.collect(Collectors.toSet());
	}
	
	private <T> Set<T> generateSetsWithDescriptions(Class<T> clz, String... descriptions) {
//		Set set = new HashSet<>();
		Set<T> set = new HashSet<>();
		
		T item;
		for (String description : descriptions) {
			try {
				Constructor<T> constructor = clz.getConstructor();
				item = (T) constructor.newInstance();
				Method setDescription = clz.getMethod("setDescription", String.class);
				setDescription.invoke(item, description);
				set.add(item);
			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return set;
	}
	
//	public UnitOfMeasure getUnitOfMeasure(String uom) {
//		
//		// FIXME: Retrieve from DB, or create new + save;
//		return new UnitOfMeasure();
//	}
	
	public Set<Ingredient> generateIngredients(Ingredient... ingredients) {
		return Arrays.asList(ingredients).stream().collect(Collectors.toSet());
	}
	
	public Ingredient createIngredient(BigDecimal bd, String description, UnitOfMeasure uom) {
		Ingredient ingredient = new Ingredient();
		ingredient.setAmount(new BigDecimal(20));
		ingredient.setDescription(description);
		ingredient.setUnitOfMeas(uom);
		
		return ingredient;
	}
	
	public UnitOfMeasure getUnitOfMeasure(String description) {
		Optional<UnitOfMeasure> uom = unitOfMeasureRepository.findByDescription(description);
		if (!uom.isPresent()) {
			UnitOfMeasure u = new UnitOfMeasure(); 
			u.setDescription(description); 
			unitOfMeasureRepository.save(u);
		}
		
		return unitOfMeasureRepository.findByDescription(description).get();
	}
	
	private void initDb() {
		
		Recipe recipe = new Recipe();
		
		Set<Category> categories = generateSetsWithDescriptions(Category.class, "Puerto-rican", "Barely edible", "Chewy");
		categories.forEach(categoryRepository::save); // TOOD: Fix if does not exist.
		recipe.setCategories(categories);
		
		recipe.setCookTime(90);
		recipe.setDescription("Perfect Guacamole");
		recipe.setDifficulty(Difficulty.KINDOFHARD);
		recipe.setImage(new Byte[] { 0, 1, 2, 3, 4 });
		
		Set<Ingredient> ingredients = generateIngredients(
				createIngredient(new BigDecimal(200), "Flour", getUnitOfMeasure("grams")),
				createIngredient(new BigDecimal(10), "Butter", getUnitOfMeasure("grams")),
				createIngredient(new BigDecimal(5), "Salt", getUnitOfMeasure("grams")),
				createIngredient(new BigDecimal(1), "Potatoes", getUnitOfMeasure("potatoes"))
				);				
		recipe.setIngredients(ingredients);
		Notes notes = new Notes();
		notes.setRecipeNotes("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of "
				+ "the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.)"
				+"Place in a bowl. 2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! Te guacamole "
				+"should be a little chunky.) Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4wDGPLAuK");
		recipe.setNotes(notes);
		
		recipe.setPrepTime(2);
		recipe.setServings(4);
		recipe.setSource("ZZ");
		recipe.setUrl("http://www.whatever.zzz");

		recipeRepository.save(recipe);
	}
	
	
	
}
