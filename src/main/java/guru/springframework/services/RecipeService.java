package guru.springframework.services;

import java.util.List;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;

public interface RecipeService {
	
	List<Recipe> getRecipes();
	Recipe findById(Long id);
	RecipeCommand saveRecipeCommand(RecipeCommand command);
	RecipeCommand findCommandById(Long id);
	void deleteById(Long long1);
}
