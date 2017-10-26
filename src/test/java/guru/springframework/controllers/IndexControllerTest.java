package guru.springframework.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import guru.springframework.services.RecipeListerService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class IndexControllerTest {
	
	@Mock
	RecipeListerService recipeLister;
	@Mock
	CategoryRepository categoryRepo;
	@Mock
	UnitOfMeasureRepository uomRepo;
	
	@Mock
	Model model;
	
	IndexController indexController;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		indexController = new IndexController(categoryRepo, uomRepo, recipeLister);	
	}
	
	@Test
	public void testMockMvc() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
		mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(view().name("index"));
		
	}
	
	@Test
	public void getIndexTest() {
		log.info("getIndexTest starts");
		
		List<Recipe> recipes = new ArrayList<>();
		recipes.add(new Recipe());
		recipes.add(new Recipe());
		when(recipeLister.getAllRecipes()).thenReturn(recipes);
		ArgumentCaptor<List<Recipe>> argumentCaptor = ArgumentCaptor.forClass(List.class);
		
		// when:
		String viewName = indexController.getIndexPage(model);
		
		// then:
		assertEquals("index", viewName);
		verify(recipeLister, times(1)).getAllRecipes();
		verify(model, times(1)).addAttribute(eq("recipes"),  argumentCaptor.capture());
		List<Recipe> setInController = argumentCaptor.getValue();
		assertEquals(2, setInController.size());
		
	}
}
