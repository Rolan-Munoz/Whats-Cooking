package com.rolanmunoz.whatscooking.application.service.impl;

import com.rolanmunoz.whatscooking.application.dto.FavoriteDTO;
import com.rolanmunoz.whatscooking.application.dto.RecipeDTO;
import com.rolanmunoz.whatscooking.application.dto.UserDTO;
import com.rolanmunoz.whatscooking.application.mapper.FavoriteMapper;
import com.rolanmunoz.whatscooking.application.mapper.RecipeMapper;
import com.rolanmunoz.whatscooking.application.mapper.UserMapper;
import com.rolanmunoz.whatscooking.application.service.FavoriteService;
import com.rolanmunoz.whatscooking.application.service.RecipeService;
import com.rolanmunoz.whatscooking.application.service.UserService;
import com.rolanmunoz.whatscooking.domain.entity.Favorite;
import com.rolanmunoz.whatscooking.domain.entity.Recipe;
import com.rolanmunoz.whatscooking.domain.entity.User;
import com.rolanmunoz.whatscooking.domain.persistence.FavoritePersistence;
import com.rolanmunoz.whatscooking.infraestructure.persistence.RecipeRepository;
import com.rolanmunoz.whatscooking.infraestructure.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoritePersistence favoritePersistence;
    private final FavoriteMapper favoriteMapper;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    @Autowired
    public FavoriteServiceImpl(FavoritePersistence favoritePersistence, FavoriteMapper favoriteMapper, UserRepository userRepository, RecipeRepository recipeRepository) {
        this.favoritePersistence = favoritePersistence;
        this.favoriteMapper = favoriteMapper;
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
    }

    @Transactional
    @Override
    public FavoriteDTO addFavorite(Long userId, Long recipeId) {
        if (isFavorite(userId, recipeId)) {
            throw new IllegalArgumentException("Item is already a favorite");
        }
        Favorite favorite = new Favorite();
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found")); // Obtén el usuario de la base de datos
        favorite.setUser(user);
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new IllegalArgumentException("Recipe not found")); // Obtén la receta de la base de datos
        favorite.setRecipe(recipe);
        Favorite savedFavorite = favoritePersistence.saveFavorite(favorite);
        return favoriteMapper.toDto(savedFavorite);
    }

    @Transactional
    @Override
    public void removeFavorite(Long userId, Long recipeId) {
        Optional<Favorite> favoriteOptional = favoritePersistence.getFavoriteIdByUserAndRecipe(userId, recipeId);

        if (favoriteOptional.isPresent()) {
            Long favoriteId = favoriteOptional.get().getId();
            favoritePersistence.deleteFavorite(favoriteId);
        } else {
            throw new IllegalArgumentException("Favorite not found");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<FavoriteDTO> getFavoritesByUser(Long userId) {
        List<Favorite> favorites = favoritePersistence.getFavoritesByUser(userId);
        return favorites.stream()
                .map(favoriteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isFavorite(Long userId, Long recipeId) {
        Optional<Favorite> favoriteOptional = favoritePersistence.getFavoriteIdByUserAndRecipe(userId, recipeId);
        return favoriteOptional.isPresent();
    }
}

