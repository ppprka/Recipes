package recipes.services;

import org.junit.Test;
import recipes.models.Recipe;
import recipes.repositorys.RecipeRepository;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class RecipeDetailServiceTest {
    Recipe recipe = new Recipe();
    RecipeDetailService recipeDetailService;
    RecipeRepository repo;
    @Test
    public void getRecipe() throws Exception {
        Recipe actual = new Recipe();
        repo.save(actual);
        Recipe expected = recipeDetailService.getRecipe(1);
        assertEquals(expected,actual);
    }
}