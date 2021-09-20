package recipes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.models.Recipe;
import recipes.services.imp.RecipeService;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}")
    public Recipe getRecipe(@PathVariable int id) {
        return recipeService.getRecipe(id);
    }

    @PostMapping("/recipe/new")
    public Map<String, Integer> addNewRecipe(@Valid @RequestBody Recipe recipe,
                                          @Autowired Principal principal) {
        return this.recipeService.saveRecipe(recipe,principal);
    }

    @DeleteMapping("/recipe/{id}")
    public Map<String, HttpStatus> deleteRecipe(@PathVariable int id,
                                               @Autowired Principal principal) {
        return recipeService.deleteRecipe(id,principal);
    }

    @PutMapping("/recipe/{id}")
    public Map<String, HttpStatus> updateRecipe(@Valid @RequestBody Recipe recipe,
                                                @PathVariable int id, @Autowired Principal principal) {
        return recipeService.updateRecipe(recipe,id,principal);
    }

    @GetMapping("/recipe/search/")
    @ResponseBody
    public List<Recipe> searchRecipe(@RequestParam(required = false) String category,
                                               @RequestParam(required = false) String name) {
        if (category == null && name == null
                || category != null && name != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (category != null) {
            return this.recipeService.getAllByCategory(category);
        } else {
            return this.recipeService.getAllByName(name);
        }
    }

}
