package com.rolanmunoz.whatscooking.infraestructure.persistence;

import com.rolanmunoz.whatscooking.domain.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByTittle(String tittle);

    @Query("SELECT r FROM Recipe r WHERE r.user.id = :userId")
    List<Recipe> findAllByUserId(@Param("userId") Long userId);

    List<Recipe> findByTittleContaining(String tittle);

    Optional<Recipe> findByUserIdAndId(Long userId, Long idRecipe);
}
