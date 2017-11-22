package guru.springframework.services;


import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.services.RecipeServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Created by jt on 6/17/17.
 */
public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void getRecipeByIdTest() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(1L);

        assertNotNull("Null recipe returned", recipeReturned);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipesTest() throws Exception {

        Recipe recipe = new Recipe();
        ArrayList<Recipe> receipesData = new ArrayList<>();
        receipesData.add(recipe);

        when(recipeRepository.findAll()).thenReturn(receipesData);
        List<Recipe> recipes = recipeService.getAllRecipes();

        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
        verify(recipeRepository, never()).findById(anyLong());
    }

    @Test
    public void testFindCommandById() {
    	Recipe recipe = new Recipe();
    	recipe.setId(3L);
    	Optional<Recipe> recipeOptional = Optional.of(recipe);
    	
    	when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
    	RecipeCommand recipeCommand = new RecipeCommand();
    	recipeCommand.setId(3L);
    	
    	when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);
    	RecipeCommand commandById = recipeService.findCommandById(3L);
    	
    	assertNotNull("Null recipe returned", commandById);
    	verify(recipeRepository, times(1)).findById(anyLong());
    	verify(recipeRepository, never()).findAll();
    	
    }
    
    
    @Test
    public void testDeleteById() {
    	Long idToDelete = 2L;
    	recipeService.deleteById(idToDelete);
    	verify(recipeRepository, times(1)).deleteById(anyLong());
    }
}
