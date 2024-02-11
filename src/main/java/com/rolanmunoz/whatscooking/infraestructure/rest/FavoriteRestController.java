package com.rolanmunoz.whatscooking.infraestructure.rest;

import com.rolanmunoz.whatscooking.application.dto.FavoriteDTO;
import com.rolanmunoz.whatscooking.application.dto.UserDTO;
import com.rolanmunoz.whatscooking.application.service.FavoriteService;
import com.rolanmunoz.whatscooking.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/favorites")
@CrossOrigin(origins = {"http://localhost:4200"})
public class FavoriteRestController {

    private final FavoriteService favoriteService;
    private final UserService userService;

    @Autowired
    public FavoriteRestController(FavoriteService favoriteService, UserService userService) {
        this.favoriteService = favoriteService;
        this.userService = userService;
    }


    @PostMapping("/{userId}/{recipeId}")
    public ResponseEntity<FavoriteDTO> addFavorite(@PathVariable Long userId, @PathVariable Long recipeId, Principal principal) {
        Optional<UserDTO> optionalUser = this.userService.getUserById(userId);
        if (optionalUser.isPresent() && principal.getName().equals(optionalUser.get().getEmail())) {
            FavoriteDTO favoriteDTO = favoriteService.addFavorite(userId, recipeId);
            return new ResponseEntity<>(favoriteDTO, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{userId}/{recipeId}")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long userId, @PathVariable Long recipeId, Principal principal) {
        Optional<UserDTO> optionalUser = this.userService.getUserById(userId);
        if (optionalUser.isPresent() && principal.getName().equals(optionalUser.get().getEmail())) {
            favoriteService.removeFavorite(userId, recipeId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<FavoriteDTO>> getFavoritesByUser(@PathVariable Long userId, Principal principal) {
        Optional<UserDTO> optionalUser = this.userService.getUserById(userId);
        if (optionalUser.isPresent() && principal.getName().equals(optionalUser.get().getEmail())) {
            List<FavoriteDTO> favorites = favoriteService.getFavoritesByUser(userId);
            return new ResponseEntity<>(favorites, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/{userId}/{recipeId}/isFavorite")
    public ResponseEntity<Boolean> isFavorite(@PathVariable Long userId, @PathVariable Long recipeId, Principal principal) {
        Optional<UserDTO> optionalUser = this.userService.getUserById(userId);
        if (optionalUser.isPresent() && principal.getName().equals(optionalUser.get().getEmail())) {
            boolean isFavorite = favoriteService.isFavorite(userId, recipeId);
            return new ResponseEntity<>(isFavorite, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


}

