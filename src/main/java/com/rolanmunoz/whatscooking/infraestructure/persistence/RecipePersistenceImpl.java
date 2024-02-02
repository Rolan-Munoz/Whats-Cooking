package com.rolanmunoz.whatscooking.infraestructure.persistence;

import com.rolanmunoz.whatscooking.domain.entity.Recipe;
import com.rolanmunoz.whatscooking.domain.persistence.RecipePersistence;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RecipePersistenceImpl implements RecipePersistence {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipePersistenceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Page<Recipe> getAllRecipes(Pageable pageable) {
        return this.recipeRepository.findAll(pageable);
    }

    @Override
    public List<Recipe> getAllRecipesFromUser(Long idUser) {
        return this.recipeRepository.findAllByUserId(idUser);
    }

    @Override
    public Optional<Recipe> getRecipeById(Long idRecipe) {
        return this.recipeRepository.findById(idRecipe);
    }

    @Override
    public Page<Recipe> getRecipeByTittle(String tittle, Pageable pageable) {
        return this.recipeRepository.findByTittleContaining(tittle, pageable);
    }

    @Override
    public Recipe saveRecipe(Recipe recipe) {
        return this.recipeRepository.save(recipe);
    }

    @Override
    public void deleteRecipe(Long idRecipe) {
        this.recipeRepository.deleteById(idRecipe);
    }

    @Override
    public Recipe updateRecipe(Recipe recipe) {
        if(this.recipeRepository.existsById(recipe.getId())) {
            return this.recipeRepository.save(recipe);
        }else {
            throw  new EntityNotFoundException("Recipe not found");
        }
    }

    @Override
    public Boolean existsByTittle(String tittle) {
        return !this.recipeRepository.findByTittle(tittle).isEmpty();
    }

    @Override
    public Optional<Recipe> getRecipeByUserIdAndRecipeId(Long userId, Long idRecipe) {
        return this.recipeRepository.findByUserIdAndId(userId, idRecipe);
    }

}
