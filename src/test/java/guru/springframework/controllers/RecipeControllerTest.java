package guru.springframework.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.commands.RecipeCommand;
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
		mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
	}
	
	@Test
	public void testRecipeShow() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		when(recipeListerService.findById(1L)).thenReturn(recipe);
		
		
		
		mockMvc.perform(get("/recipe/1/show"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/show"))
			.andExpect(model().attributeExists("recipe"));
	}
	
	@Test
	public void testGetNewRecipeForm() throws Exception {
		mockMvc.perform(post("/recipe/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/recipeform"))
			.andExpect(model().attributeExists("recipe"));		
	}
	
	@Test
	public void testPostNewRecipeForm() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId(2L);
		
		when(recipeListerService.saveRecipeCommand(any())).thenReturn(command);
		
		mockMvc.perform(post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "")
				.param("description", "some string"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/recipe/2/show"));
	}
	
	@Test
	public void testGetUpdateView() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId(2L);

		when(recipeListerService.findCommandById(anyLong())).thenReturn(command);
		
		mockMvc.perform(get("/recipe/1/update"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/recipeform"))
			.andExpect(model().attributeExists("recipe"));		
	}
	
	@Test
	public void testDelete() throws Exception {
		 
		
		mockMvc.perform(get("/recipe/1/delete"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/"));
			
		verify(recipeListerService, times(1)).deleteById(anyLong());			
	}
}
