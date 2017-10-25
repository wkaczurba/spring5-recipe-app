package guru.springframework.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.domain.Category;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import guru.springframework.services.RecipeListerService;

@Controller
public class IndexController {
	
	private CategoryRepository categoryRepository;
	private UnitOfMeasureRepository unitOfMeasureRepository;
	private RecipeListerService recipeListerService;
	
	public IndexController(CategoryRepository categoryRepository,
			UnitOfMeasureRepository unitOfMeasureRepository,
			RecipeListerService recipeListerService) {
		
		this.categoryRepository = categoryRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.recipeListerService = recipeListerService;
	}
  
	@RequestMapping({"/", "", "/index"})
	public String getIndexPage(Model model) {
		System.out.println("index requested");
		
		Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
		Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription("Tablespoon");
		
//HERE!		model.addAttribute(recipes);
		System.out.println("category=" + categoryOptional.get().getId());
		System.out.println("unitOfMeasure=" + unitOfMeasure.get().getId());
	
		return "index";
	}

	@RequestMapping("/recipe")
	public String getRecipes(Model model) {
		List<Recipe> recipes = recipeListerService.getAllRecipes();
		//model.addAttribute("recipes", recipes);
		model.addAttribute(recipes);
		
		return "recipes";
	}
	
}
