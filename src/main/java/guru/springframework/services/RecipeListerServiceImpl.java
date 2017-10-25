package guru.springframework.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;

@Service
public class RecipeListerServiceImpl implements RecipeListerService {
	
	RecipeRepository recipeRepository;
	
	@Autowired
	public RecipeListerServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository; 
	}

	@Override
	public List<Recipe> getAllRecipes() {
		List<Recipe> list = new ArrayList<Recipe>();
		recipeRepository.findAll().forEach(list::add);
		return list;
	}

}
