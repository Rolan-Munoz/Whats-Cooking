package com.rolanmunoz.whatscooking.infraestructure.persistence;

import com.rolanmunoz.whatscooking.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findAllByRecipeId(Long recipeId);
    List<Comment> findAllByUserId(Long userId);
    List<Comment> findTop5ByOrderByDateDesc();
}
