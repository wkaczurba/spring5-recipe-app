package guru.springframework.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;

//@SpringBootTest
//@RunWith(SpringBoot.)
public class IngredientControllerTest {
	
	@Mock
	RecipeService recipeService;
	@Mock
	IngredientService ingredientService;
	@Mock
	UnitOfMeasureService unitOfMeasureService;
	
	IngredientController controller;
	MockMvc mockMvc;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		controller = new IngredientController(ingredientService, recipeService, unitOfMeasureService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testListIngredients() throws Exception {
		
		// when
		RecipeCommand cmd = new RecipeCommand();
		cmd.setId(2L);
		when(recipeService.findCommandById(anyLong())).thenReturn(cmd);
		
		// then
		mockMvc.perform(get("/recipe/2/ingredients"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/ingredient/list"))
			.andExpect(model().attributeExists("recipe"));
		
		//verify
		verify(recipeService, times(1)).findCommandById(2L);
	}
	
	@Test
	public void testDeleteIngredient() throws Exception {
		mockMvc.perform(
				get("/recipe/1/ingredient/2/delete"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/recipe/1/ingredients"));
		
	    verify(ingredientService, times(1)).deleteByRecipeAndIngredientId(1L, 2L);
	}
	
	
}
