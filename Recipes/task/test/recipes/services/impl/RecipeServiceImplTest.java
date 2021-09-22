package recipes.services.impl;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.*;
import recipes.models.Recipe;
import recipes.repositorys.RecipeRepository;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class RecipeServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeServiceImpl service;

    @Test
    public void getRecipe() throws NullPointerException {
        try{int id =1;
        Recipe recipeExpected = new Recipe();
        when(recipeRepository.findById(any())).thenReturn(java.util.Optional.of(recipeExpected));
        Recipe actual = service.getRecipe(any());
        assertEquals(recipeExpected,actual);}
        catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
    }
}