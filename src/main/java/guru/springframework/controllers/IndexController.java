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
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
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

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        log.debug("Getting Index page");

        model.addAttribute("recipes", recipeListerService.getAllRecipes());

        return "index";
    }	
	
}
