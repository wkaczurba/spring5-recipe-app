package guru.springframework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeListerService;


@Controller
public class RecipeController {
	
	RecipeListerService recipeListerService;
	
	@Autowired
	public RecipeController(RecipeListerService recipeService) {
		this.recipeListerService = recipeService;
	}
	
	@RequestMapping("/recipe/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		return "recipe/recipeForm";
	}
	
	@RequestMapping("/recipe/{id}/show")
	public String showById(@PathVariable String id, Model model) {
		model.addAttribute(recipeListerService.findById(new Long(id)));
		
		return "recipe/show";
	}
	
	@RequestMapping("/recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeListerService.findById(Long.valueOf(id)));
		return "recipe/recipeform";
	}
	
	@PostMapping
	@RequestMapping("recipe") // ?
	public String saveOrUpdate(@ModelAttribute RecipeCommand command) {

		RecipeCommand savedCommand =recipeListerService.saveRecipeCommand(command);
		return "redirect:/recipe/" + savedCommand.getId() + "/show";
	}	
}
