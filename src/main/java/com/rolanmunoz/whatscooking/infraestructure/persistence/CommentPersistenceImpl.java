package com.rolanmunoz.whatscooking.infraestructure.persistence;

import com.rolanmunoz.whatscooking.domain.entity.Comment;
import com.rolanmunoz.whatscooking.domain.persistence.CommentPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentPersistenceImpl implements CommentPersistence {

    private final CommentRepository repository;

    @Autowired
    public CommentPersistenceImpl(CommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Comment> getCommentsByRecipe(Long recipeId) {
        return this.repository.findAllByRecipeId(recipeId);
    }

    @Override
    public List<Comment> getCommentsByUser(Long userId) {
        return this.repository.findAllByUserId(userId);
    }

    @Override
    public Comment saveComment(Comment comment) {
        return this.repository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        this.repository.deleteById(commentId);
    }

    @Override
    public Optional<Comment> getCommentById(Long commentId) {
        return this.repository.findById(commentId);
    }

    @Override
    public List<Comment> getRecentComments() {
        return this.repository.findTop5ByOrderByDateDesc();
    }
}
