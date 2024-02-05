package com.rolanmunoz.whatscooking.infraestructure.persistence;

import com.rolanmunoz.whatscooking.domain.entity.Favorite;
import com.rolanmunoz.whatscooking.domain.persistence.FavoritePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FavoritePersistenceImpl implements FavoritePersistence {

    private final FavoriteRepository repository;

    @Autowired
    public FavoritePersistenceImpl(FavoriteRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Favorite> getFavoritesByUser(Long userId) {
        return this.repository.findAllByUserId(userId);
    }

    @Override
    public Optional<Favorite> getFavoriteIdByUserAndRecipe(Long userId, Long recipeId) {
        return this.repository.findByUserIdAndRecipeId(userId, recipeId);
    }

    @Override
    public Favorite saveFavorite(Favorite favorite) {
        return this.repository.save(favorite);
    }

    @Override
    public void deleteFavorite(Long favoriteId) {
        this.repository.deleteById(favoriteId);
    }
}
