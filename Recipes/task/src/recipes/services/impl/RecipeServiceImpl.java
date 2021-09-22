package recipes.services.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.models.Recipe;
import recipes.repositorys.RecipeRepository;
import recipes.services.RecipeService;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }


    @Override
    public Recipe getRecipe(int id) {

        return recipeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Recipe> getAllByCategory(String category) {
        return recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    @Override
    public List<Recipe> getAllByName(String name){
        return recipeRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteRecipe(int id,Principal principal) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!recipe.getAuthor().equalsIgnoreCase(principal.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        recipeRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public Map<String,Integer> saveRecipe(Recipe recipe, Principal principal){
        recipe.setAuthor(principal.getName());
        recipeRepository.save(recipe);
        Map<String,Integer> resp = new HashMap<>();
        resp.put("id", recipe.getId());
        return resp;
    }

    @Override
    public ResponseEntity<HttpStatus> updateRecipe(Recipe recipe,int id,Principal principal){
        Recipe recipeCheck = recipeRepository.findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!recipe.getAuthor().equalsIgnoreCase(principal.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        recipe.setId(id);
        recipe.setAuthor(recipeCheck.getAuthor());
        recipe.onCreated();
        recipeRepository.save(recipe);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
