package com.rolanmunoz.whatscooking.application.service;

import com.rolanmunoz.whatscooking.application.dto.RecipeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RecipeService {

        Page<RecipeDTO> getAllRecipes(Pageable pageable);

        Optional<RecipeDTO> getRecipeById(Long idRecipe);
        Page<RecipeDTO> getRecipeByTittle(String tittle, Pageable pageable);
        List<RecipeDTO> getAllRecipesFromUser(Long idUser);
        RecipeDTO saveRecipe(RecipeDTO recipeDTO, String username);
        void deleteRecipe(Long idRecipe);
        RecipeDTO updateRecipe(Long id, RecipeDTO recipeDTO);
        Boolean existsByTittle(String tittle);

        Optional<RecipeDTO> getRecipeByIdFromUser(Long userId, Long idRecipe);
}
