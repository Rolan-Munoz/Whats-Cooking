package com.rolanmunoz.whatscooking.domain.persistence;

import com.rolanmunoz.whatscooking.domain.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentPersistence {
    List<Comment> getCommentsByRecipe(Long recipeId);

    List<Comment> getCommentsByUser(Long userId);

    Comment saveComment(Comment comment);

    void deleteComment(Long commentId);

    Optional<Comment> getCommentById(Long commentId);

    List<Comment> getRecentComments();
}
