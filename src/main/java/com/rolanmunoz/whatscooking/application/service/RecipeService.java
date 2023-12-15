package com.rolanmunoz.whatscooking.application.service;

import com.rolanmunoz.whatscooking.application.dto.RecipeDTO;

import java.util.List;
import java.util.Optional;

public interface RecipeService {

        List<RecipeDTO> getAllRecipes();
        Optional<RecipeDTO> getRecipeById(Long idRecipe);
        List<RecipeDTO> getRecipeByTittle(String tittle);
        List<RecipeDTO> getAllRecipesFromUser(Long idUser);
        RecipeDTO saveRecipe(RecipeDTO recipeDTO, String username);
        void deleteRecipe(Long idRecipe);
        RecipeDTO updateRecipe(Long id, RecipeDTO recipeDTO);
        Boolean existsByTittle(String tittle);

        Optional<RecipeDTO> getRecipeByIdFromUser(Long userId, Long idRecipe);
}
