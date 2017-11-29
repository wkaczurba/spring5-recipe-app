package guru.springframework.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.h2.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;

@Controller
public class ImageController {

	// post image.
	ImageService imageService;
	RecipeService recipeService;
	
	@Autowired
	public ImageController(ImageService imageService, RecipeService recipeService) {
		this.imageService = imageService;
		this.recipeService = recipeService;
	}
	
	@PostMapping("/recipe/{recipeId}/image") 
	public String assignImage(
			@RequestParam("imagefile") MultipartFile file, 
			@PathVariable String recipeId, 
			Model model) {
		
		imageService.saveImageFile(Long.valueOf(recipeId), file);
		
		return "redirect:/recipe/" + recipeId + "/show";
	}
	
	@GetMapping("/recipe/{recipeId}/image")
	public String showUploadForm(@PathVariable String recipeId, Model model) {
		RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
		
		model.addAttribute("recipe", recipeCommand);
		return "recipe/imageuploadform";
	}
	
	@GetMapping("/recipe/{recipeId}/recipeimage")
	public void renderImageFromDB(@PathVariable String recipeId, HttpServletResponse response) throws IOException {
		
		RecipeCommand recipe = recipeService.findCommandById(Long.valueOf(recipeId));
		Byte[] image = recipe.getImage();
		byte[] bytes = new byte[image.length];
		
		OutputStream outputStream = response.getOutputStream();
		response.setContentType("image/jpeg");
		for (int i = 0; i < image.length; i++) {
			bytes[i] = image[i];
			//response.getOutputStream()
			outputStream.write(bytes[i]);
		}
//		InputStream inputStream = new ByteArrayInputStream(bytes);
		
//		IOUtils.copy(inputStream, outputStream);
	}
	
	
	// get picture?
//	public HttpResponse getPicture() {
//		
//	}
	
}
