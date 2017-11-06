package guru.springframework.services;

import static org.junit.Assert.*;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RecipeServiceIT {
	
	public final String NEW_DESCRIPTION = "SOME DESC.";

	@Autowired
	RecipeListerService recipeListerService;
	
	@Autowired
	RecipeRepository recipeRepository;
	
	@Autowired
	RecipeToRecipeCommand recipeToRecipeCommand;
	
	@Autowired
	RecipeCommandToRecipe recipeCommandToRecipe;
	
	@Test
	@Transactional
	public void recipeServiceTest() {
		Iterable<Recipe> recipes = recipeRepository.findAll();
		Recipe testRecipe = recipes.iterator().next();
		RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);
		
		recipeListerService.getAllRecipes();
		
		// when:
		testRecipeCommand.setDescription(NEW_DESCRIPTION);
		RecipeCommand savedRecipeCommand = recipeListerService.saveRecipeCommand(testRecipeCommand);

		// then
		assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
		assertEquals(testRecipeCommand.getId(), savedRecipeCommand.getId());
		assertEquals(testRecipeCommand.getCategories().size(), savedRecipeCommand.getCategories().size());
		assertEquals(testRecipeCommand.getIngredients().size(), savedRecipeCommand.getIngredients().size());
	}
	
	
}