package guru.springframework.services;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.controllers.RecipeController;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;

@Service
public class ImageServiceImpl implements ImageService {
	
	RecipeRepository recipeRepository;
	
	@Autowired
	public ImageServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	@Override
	public void saveImageFile(Long recipeId, MultipartFile file) {
		try {
			byte[] bytes = file.getBytes();
			Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
			Recipe recipe = recipeOptional.get();
			
			Byte[] byteArr = new Byte[bytes.length];
			for (int i = 0; i < bytes.length; i++) {
				byteArr[i] = bytes[i];
				//recipe.setImage(bytes);
			}
			recipe.setImage(byteArr);
			recipeRepository.save(recipe);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
