package guru.springframework.services;

import org.springframework.stereotype.Service;

import guru.springframework.commands.IngredientCommand;

public interface IngredientService {
	IngredientCommand findByRecipeIdAndIngredientId(long recipeId, long ingredientId);
	IngredientCommand saveIngredient(IngredientCommand command);
	void deleteById(Long recipeId, Long idToDelete);
	
}
