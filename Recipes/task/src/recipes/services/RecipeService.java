package recipes.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import recipes.models.Recipe;
import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface RecipeService {

    Map<String,Integer> saveRecipe(Recipe recipe, Principal principal);

    Recipe getRecipe(int id);

    List<Recipe> getAllByCategory(String category);

    List<Recipe> getAllByName(String name);

    ResponseEntity<HttpStatus> deleteRecipe(int id, Principal principal);

    ResponseEntity<HttpStatus> updateRecipe(Recipe recipe,int id,Principal principal);
}
