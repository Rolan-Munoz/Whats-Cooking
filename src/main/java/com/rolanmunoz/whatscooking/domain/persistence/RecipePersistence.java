package com.rolanmunoz.whatscooking.domain.persistence;

import com.rolanmunoz.whatscooking.domain.entity.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RecipePersistence {
    Page<Recipe> getAllRecipes(Pageable pageable);
    Optional<Recipe> getRecipeById(Long idRecipe);

    Page<Recipe> getRecipeByTittle(String tittle, Pageable pageable);
    List<Recipe> getAllRecipesFromUser(Long idUser);
    Recipe saveRecipe(Recipe recipe);
    void deleteRecipe(Long idRecipe);
    Recipe updateRecipe(Recipe recipe);
    Boolean existsByTittle(String tittle);
    Optional<Recipe> getRecipeByUserIdAndRecipeId(Long userId, Long idRecipe);


}
