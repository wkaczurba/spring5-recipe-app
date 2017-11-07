package guru.springframework.services;

import java.util.List;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;

public interface RecipeListerService {
	
	List<Recipe> getAllRecipes();
	Recipe findById(Long id);
	RecipeCommand saveRecipeCommand(RecipeCommand command);
	RecipeCommand findCommandById(Long id);
}
