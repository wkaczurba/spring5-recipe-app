package guru.springframework.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RecipeListerServiceImpl implements RecipeListerService {
	
	RecipeRepository recipeRepository;
	
	@Autowired
	public RecipeListerServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository; 
	}

	@Override
	public List<Recipe> getAllRecipes() {
		log.debug("I am in the service");
		
		List<Recipe> list = new ArrayList<Recipe>();
		recipeRepository.findAll().forEach(list::add);
		return list;
	}

}
