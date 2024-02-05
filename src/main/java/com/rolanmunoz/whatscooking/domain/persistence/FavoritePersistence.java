package com.rolanmunoz.whatscooking.domain.persistence;

import com.rolanmunoz.whatscooking.domain.entity.Favorite;

import java.util.List;
import java.util.Optional;

public interface FavoritePersistence {
    List<Favorite> getFavoritesByUser(Long userId);

    Optional<Favorite> getFavoriteIdByUserAndRecipe(Long userId, Long recipeId);

    Favorite saveFavorite(Favorite favorite);

    void deleteFavorite(Long favoriteId);


}
