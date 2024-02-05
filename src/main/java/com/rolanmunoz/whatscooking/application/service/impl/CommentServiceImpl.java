package com.rolanmunoz.whatscooking.application.service.impl;

import com.rolanmunoz.whatscooking.application.dto.CommentDTO;
import com.rolanmunoz.whatscooking.application.mapper.CommentMapper;
import com.rolanmunoz.whatscooking.application.service.CommentService;
import com.rolanmunoz.whatscooking.domain.entity.Comment;
import com.rolanmunoz.whatscooking.domain.entity.Recipe;
import com.rolanmunoz.whatscooking.domain.entity.User;
import com.rolanmunoz.whatscooking.domain.persistence.CommentPersistence;
import com.rolanmunoz.whatscooking.infraestructure.persistence.RecipeRepository;
import com.rolanmunoz.whatscooking.infraestructure.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentPersistence commentPersistence;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    @Autowired
    public CommentServiceImpl(CommentPersistence commentPersistence, CommentMapper commentMapper, UserRepository userRepository, RecipeRepository recipeRepository) {
        this.commentPersistence = commentPersistence;
        this.commentMapper = commentMapper;
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
    }
    @Transactional
    @Override
    public CommentDTO addComment(Long userId, Long recipeId, String content) {
        Comment comment = new Comment();
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found")); // Obtén el usuario de la base de datos
        comment.setUser(user);
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new IllegalArgumentException("Recipe not found")); // Obtén la receta de la base de datos
        comment.setRecipe(recipe);
        comment.setText(content);
        Comment savedComment = commentPersistence.saveComment(comment);
        return commentMapper.toDto(savedComment);
    }

    @Transactional
    @Override
    public void removeComment(Long commentId) {
        commentPersistence.deleteComment(commentId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDTO> getCommentsByUser(Long userId) {
        List<Comment> comments = commentPersistence.getCommentsByUser(userId);
        return comments.stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDTO> getCommentsByRecipe(Long recipeId) {
        List<Comment> comments = commentPersistence.getCommentsByRecipe(recipeId);
        return comments.stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<CommentDTO> getCommentById(Long commentId) {
        Optional<Comment> commentOptional = commentPersistence.getCommentById(commentId);
        return commentOptional.map(commentMapper::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDTO> getRecentComments() {
        List<Comment> recentComments = commentPersistence.getRecentComments();
        return recentComments.stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }
}
