package recipes.services;

import recipes.models.Recipe;

import java.util.List;

public interface RecipeService {

    List<Recipe> getAllRecipes();

    void deleteRecipe(int id);

    void saveRecipe(Recipe recipe);

    Recipe getRecipe(int id);

    List<Recipe> getAllByKeyValue(String key, String value);
}
