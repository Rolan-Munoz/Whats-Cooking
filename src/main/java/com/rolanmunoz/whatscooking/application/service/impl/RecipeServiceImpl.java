package com.rolanmunoz.whatscooking.application.service.impl;

import com.rolanmunoz.whatscooking.application.dto.RecipeDTO;
import com.rolanmunoz.whatscooking.application.mapper.RecipeMapper;
import com.rolanmunoz.whatscooking.application.service.RecipeService;
import com.rolanmunoz.whatscooking.domain.entity.Recipe;
import com.rolanmunoz.whatscooking.domain.entity.User;
import com.rolanmunoz.whatscooking.domain.persistence.RecipePersistence;
import com.rolanmunoz.whatscooking.domain.persistence.UserPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipePersistence recipePersistence;
    private final RecipeMapper recipeMapper;
    private final UserPersistence userPersistence;

    @Autowired
    public RecipeServiceImpl(RecipePersistence recipePersistence, RecipeMapper recipeMapper, UserPersistence userPersistence) {
        this.recipePersistence = recipePersistence;
        this.recipeMapper = recipeMapper;
        this.userPersistence= userPersistence;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<RecipeDTO> getAllRecipes(Pageable pageable) {
        Page<Recipe> recipes = this.recipePersistence.getAllRecipes(pageable);
        return recipes.map(this.recipeMapper::toDto);
    }


    @Transactional(readOnly = true)
    @Override
    public Optional<RecipeDTO> getRecipeById(Long idRecipe) {
        return this.recipePersistence.getRecipeById(idRecipe).map(recipeMapper::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<RecipeDTO> getRecipeByTittle(String tittle, Pageable pageable) {
        Page<Recipe> recipes = this.recipePersistence.getRecipeByTittle(tittle, pageable);
        return recipes.map(recipeMapper::toDto);
    }


    @Transactional(readOnly = true)
    @Override
    public List<RecipeDTO> getAllRecipesFromUser(Long idUser) {
        List<Recipe> recipes = this.recipePersistence.getAllRecipesFromUser(idUser);
        return recipes.stream().map(this.recipeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public RecipeDTO saveRecipe(RecipeDTO recipeDTO, String username) {
        if (recipeDTO == null) {
            throw new IllegalArgumentException("La receta no puede ser nula");
        }
        User user = userPersistence.getByEmail(username)
                .orElseThrow(() -> new RuntimeException("El usuario no existe en la base de datos"));
        Recipe recipe = new Recipe();
        recipe.setTittle(recipeDTO.getTittle());
        recipe.setIngredients(recipeDTO.getIngredients());
        recipe.setDescription(recipeDTO.getDescription());
        recipe.setInstructions(recipeDTO.getInstructions());
        recipe.setImage(recipeDTO.getImage());
        recipe.setVideo(recipeDTO.getVideo());
        recipe.setUser(user);

        Recipe savedRecipe = recipePersistence.saveRecipe(recipe);
        return recipeMapper.toDto(savedRecipe);
    }

    @Transactional
    @Override
    public void deleteRecipe(Long idRecipe) {
        this.recipePersistence.deleteRecipe(idRecipe);
    }


    @Transactional
    @Override
    public RecipeDTO updateRecipe(Long id, RecipeDTO recipeDTO) {
        Recipe existingRecipe = this.recipePersistence.getRecipeById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));
        if (recipeDTO.getTittle() != null && !recipeDTO.getTittle().isEmpty()) {
            existingRecipe.setTittle(recipeDTO.getTittle());
        }
        if (recipeDTO.getIngredients() != null && !recipeDTO.getIngredients().isEmpty()) {
            existingRecipe.setIngredients(recipeDTO.getIngredients());
        }
        if (recipeDTO.getDescription() != null && !recipeDTO.getDescription().isEmpty()) {
            existingRecipe.setDescription(recipeDTO.getDescription());
        }
        if (recipeDTO.getInstructions() != null && !recipeDTO.getInstructions().isEmpty()) {
            existingRecipe.setInstructions(recipeDTO.getInstructions());
        }
        if (recipeDTO.getImage() != null) {
            existingRecipe.setImage(recipeDTO.getImage());
        }
        if (recipeDTO.getVideo() != null) {
            existingRecipe.setVideo(recipeDTO.getVideo());
        }
        Recipe updatedRecipe = this.recipePersistence.saveRecipe(existingRecipe);
        return this.recipeMapper.toDto(updatedRecipe);
    }


    @Transactional(readOnly = true)
    @Override
    public Boolean existsByTittle(String tittle) {
        return this.recipePersistence.existsByTittle(tittle);
    }

    @Transactional
    @Override
    public Optional<RecipeDTO> getRecipeByIdFromUser(Long userId, Long idRecipe) {
        return this.recipePersistence.getRecipeByUserIdAndRecipeId(userId, idRecipe)
                .map(recipeMapper::toDto);
    }
}
