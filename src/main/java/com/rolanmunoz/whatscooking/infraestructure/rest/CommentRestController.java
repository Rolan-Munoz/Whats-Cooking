package com.rolanmunoz.whatscooking.infraestructure.rest;

import com.rolanmunoz.whatscooking.application.dto.CommentDTO;
import com.rolanmunoz.whatscooking.application.dto.UserDTO;
import com.rolanmunoz.whatscooking.application.service.CommentService;
import com.rolanmunoz.whatscooking.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentRestController {

    private final CommentService commentService;
    private final UserService userService;

    @Autowired
    public CommentRestController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @PostMapping("/{userId}/{recipeId}")
    public ResponseEntity<CommentDTO> addComment(@PathVariable Long userId, @PathVariable Long recipeId, @RequestBody String content, Principal principal) {
        Optional<UserDTO> optionalUser = this.userService.getUserById(userId);
        if (optionalUser.isPresent() && principal.getName().equals(optionalUser.get().getEmail())) {
            CommentDTO commentDTO = commentService.addComment(userId, recipeId, content);
            return new ResponseEntity<>(commentDTO, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> removeComment(@PathVariable Long commentId, Principal principal) {
        Optional<CommentDTO> optionalComment = this.commentService.getCommentById(commentId);
        if (optionalComment.isPresent()) {
            Long userId = optionalComment.get().getUserId();
            Optional<UserDTO> optionalUser = this.userService.getUserById(userId);
            if (optionalUser.isPresent() && principal.getName().equals(optionalUser.get().getEmail())) {
                commentService.removeComment(commentId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByUser(@PathVariable Long userId, Principal principal) {
        Optional<UserDTO> optionalUser = this.userService.getUserById(userId);
        if (optionalUser.isPresent() && principal.getName().equals(optionalUser.get().getEmail())) {
            List<CommentDTO> comments = commentService.getCommentsByUser(userId);
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByRecipe(@PathVariable Long recipeId) {
        List<CommentDTO> comments = commentService.getCommentsByRecipe(recipeId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long commentId) {
        Optional<CommentDTO> optionalComment = commentService.getCommentById(commentId);
        if (optionalComment.isPresent()) {
            return new ResponseEntity<>(optionalComment.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("recent")
    public ResponseEntity<List<CommentDTO>> getRecentComments() {
        List<CommentDTO> recentComments = commentService.getRecentComments();
        return new ResponseEntity<>(recentComments, HttpStatus.OK);
    }


}
