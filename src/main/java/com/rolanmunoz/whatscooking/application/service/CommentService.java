package com.rolanmunoz.whatscooking.application.service;

import com.rolanmunoz.whatscooking.application.dto.CommentDTO;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    CommentDTO addComment(Long userId, Long recipeId, String content);

    void removeComment(Long commentId);

    List<CommentDTO> getCommentsByUser(Long userId);

    List<CommentDTO> getCommentsByRecipe(Long recipeId);

    Optional<CommentDTO> getCommentById(Long commentId);

    List<CommentDTO> getRecentComments();
}
