package guru.springframework.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeListerServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class RecipeControllerTest {
	
	MockMvc mockMvc;
	
	
	RecipeController recipeController;
	
	@Mock
	RecipeListerServiceImpl recipeListerService;
		
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		recipeController = new RecipeController(recipeListerService);
	}
	
	@Test
	public void testRecipeShow() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		when(recipeListerService.findById(1L)).thenReturn(recipe);
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
		
		mockMvc.perform(get("/recipe/show/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/show"))
			.andExpect(model().attributeExists("recipe"));
	}
}
