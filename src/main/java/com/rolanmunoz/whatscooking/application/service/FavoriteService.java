package com.rolanmunoz.whatscooking.application.service;

import com.rolanmunoz.whatscooking.application.dto.FavoriteDTO;

import java.util.List;

public interface FavoriteService {

    FavoriteDTO addFavorite(Long userId, Long recipeId);

    void removeFavorite(Long userId, Long recipeId);

    List<FavoriteDTO> getFavoritesByUser(Long userId);

    boolean isFavorite(Long userId, Long recipeId);
}
