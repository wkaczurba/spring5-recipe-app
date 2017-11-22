package guru.springframework.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {
	
	RecipeRepository recipeRepository;
	RecipeCommandToRecipe recipeCommandToRecipe;
	RecipeToRecipeCommand recipeToRecipeCommand;
	
	@Autowired
	public RecipeServiceImpl(
			RecipeRepository recipeRepository, 
			RecipeCommandToRecipe recipeCommandToRecipe,
			RecipeToRecipeCommand recipeToRecipeCommand) {
		this.recipeRepository = recipeRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
	public List<Recipe> getAllRecipes() {
		log.debug("I am in the service");
		
		List<Recipe> list = new ArrayList<Recipe>();
		recipeRepository.findAll().forEach(list::add);
		return list;
	}
	
	@Override
	public Recipe findById(Long id) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(id);
		if (recipeOptional.isPresent()) {
			return recipeOptional.get();
		} else {
			throw new RuntimeException("Recipe id="+id+" not present.");
		}
	}
		
	
	@Override
	@Transactional
	public RecipeCommand saveRecipeCommand(RecipeCommand command) {
		Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
		Recipe savedRecipe = recipeRepository.save(detachedRecipe);
		log.debug("Savedrecipe id=" + savedRecipe.getId());
		return recipeToRecipeCommand.convert(savedRecipe);
	}
	
	@Override
	@Transactional
	public RecipeCommand findCommandById(Long id) {
		return recipeToRecipeCommand.convert(findById(id));		
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		recipeRepository.deleteById(Long.valueOf(id));
		
	}
	
	
	
}
