package recipes.services.imp;

import org.springframework.http.HttpStatus;
import recipes.models.Recipe;
import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface RecipeService {

    Map<String, Integer> saveRecipe(Recipe recipe, Principal principal);

    Recipe getRecipe(int id);

    List<Recipe> getAllByCategory(String category);

    List<Recipe> getAllByName(String name);

    Map<String, HttpStatus> deleteRecipe(int id, Principal principal);

    Map<String,HttpStatus> updateRecipe(Recipe recipe,int id,Principal principal);
}
