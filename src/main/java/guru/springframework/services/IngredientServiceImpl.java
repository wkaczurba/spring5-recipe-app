package guru.springframework.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {
	
	
	private final RecipeRepository recipeRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	
	public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
			RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
		this.recipeRepository = recipeRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
	}

	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(long recipeId, long ingredientId) {
		
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		if (!recipeOptional.isPresent()) {
			log.error("Recipe not found. Id: " + recipeId);
		}
		
		Recipe recipe = recipeOptional.get();
		
		Optional<IngredientCommand> ingredientCommandOptional = recipe
				.getIngredients()
				.stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.map(ingredientToIngredientCommand::convert)
				.findFirst();
		
		if (!ingredientCommandOptional.isPresent()) {
			log.error("Ingredient optional not found. Id: " + ingredientId);
		}
		
		return ingredientCommandOptional.get();
	}

	@Override
	@Transactional
	public IngredientCommand saveIngredient(IngredientCommand command) {
		
		Optional<Recipe> recipeOptional = recipeRepository.findById(command.getId());
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Long recipeId, Long idToDelete) {
		// TODO Auto-generated method stub
		
	}

}
