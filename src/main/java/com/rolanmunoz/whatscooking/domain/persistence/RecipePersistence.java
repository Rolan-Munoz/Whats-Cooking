package com.rolanmunoz.whatscooking.domain.persistence;

import com.rolanmunoz.whatscooking.domain.entity.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipePersistence {
    List<Recipe> getAllRecipes();
    Optional<Recipe> getRecipeById(Long idRecipe);

    List<Recipe> getRecipeByTittle(String tittle);
    List<Recipe> getAllRecipesFromUser(Long idUser);
    Recipe saveRecipe(Recipe recipe);
    void deleteRecipe(Long idRecipe);
    Recipe updateRecipe(Recipe recipe);
    Boolean existsByTittle(String tittle);
    Optional<Recipe> getRecipeByUserIdAndRecipeId(Long userId, Long idRecipe);


}
