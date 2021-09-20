package recipes.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.models.Recipe;
import recipes.repositorys.RecipeRepository;
import recipes.services.imp.RecipeService;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RecipeDetailService implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeDetailService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }


    @Override
    public Recipe getRecipe(int id) {
        Recipe recipe = null;
        Optional<Recipe> rcp = recipeRepository.findById(id);
        if (rcp.isPresent()) {
            recipe = rcp.get();
        }
        if (recipe == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return recipe;
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
    public Map<String,HttpStatus> deleteRecipe(int id,Principal principal) {
        Recipe recipe = null;
        Optional<Recipe> rcp = recipeRepository.findById(id);
        if (rcp.isPresent()) {
            recipe = rcp.get();
        }
        if (recipe == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!recipe.getAuthor().equalsIgnoreCase(principal.getName())) {
            return Map.of("wrong user", HttpStatus.FORBIDDEN);
        }
        recipeRepository.deleteById(id);
        return Map.of("deleted",HttpStatus.NO_CONTENT);
    }

    @Override
    public Map<String,Integer> saveRecipe(Recipe recipe, Principal principal){
        recipe.setAuthor(principal.getName());
        Recipe addedRecipe = recipeRepository.save(recipe);
        return Map.of("id",addedRecipe.getId());
    }

    @Override
    public Map<String,HttpStatus> updateRecipe(Recipe recipe,int id,Principal principal){
        Recipe recipeUpdate = null;
        Optional<Recipe> rcp = recipeRepository.findById(id);
        if (rcp.isPresent()) {
            recipeUpdate = rcp.get();
        }
        if (recipeUpdate == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!recipeUpdate.getAuthor().equalsIgnoreCase(principal.getName())) {
            return Map.of("wrong user", HttpStatus.FORBIDDEN);
        }
        recipe.setId(id);
        recipe.setAuthor(recipeUpdate.getAuthor());
        recipe.onCreated();
        recipeRepository.save(recipe);
        return Map.of("updated",HttpStatus.NO_CONTENT);
    }
}
