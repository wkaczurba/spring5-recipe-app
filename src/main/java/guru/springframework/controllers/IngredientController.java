package guru.springframework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class IngredientController {
	
    private final IngredientService ingredientService;
    private final RecipeService recipeService;
    private final UnitOfMeasureService unitOfMeasureService;

	@Autowired
    public IngredientController(IngredientService ingredientService, RecipeService recipeService, UnitOfMeasureService unitOfMeasureService) {
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
        this.unitOfMeasureService = unitOfMeasureService;
    }
	
	//
	
	@GetMapping
	@RequestMapping("/recipe/{id}/ingredients")
	public String getIngredients(@PathVariable String id, Model model) {
		log.debug("Getting ingredients for id=" + id);
		RecipeCommand recipe = recipeService.findCommandById(Long.valueOf(id));
		model.addAttribute("recipe", recipe);
		
		return "recipe/ingredient/list";
	}
}
