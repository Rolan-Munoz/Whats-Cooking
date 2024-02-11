package com.rolanmunoz.whatscooking.infraestructure.rest;

import com.rolanmunoz.whatscooking.application.dto.RecipeDTO;
import com.rolanmunoz.whatscooking.application.dto.UserDTO;
import com.rolanmunoz.whatscooking.application.service.RecipeService;
import com.rolanmunoz.whatscooking.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class RecipeRestController {

    private final RecipeService recipeService;
    private final UserService userService;

    @Autowired
    public RecipeRestController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }

    @GetMapping(value = "/recipes", produces = "application/json")
    public ResponseEntity<Page<RecipeDTO>> getAllRecipes(Pageable pageable) {
        Page<RecipeDTO> recipes = this.recipeService.getAllRecipes(pageable);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }


    @GetMapping(value = "/recipes/{id}", produces = "application/json")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable Long id) {
        Optional<RecipeDTO> recipe = this.recipeService.getRecipeById(id);
        return recipe.map(recipeDTO -> new ResponseEntity<>(recipeDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/recipes/tittle/{tittle}", produces = "application/json")
    public ResponseEntity<Page<RecipeDTO>> getRecipeByTittle(@PathVariable String tittle, Pageable pageable) {
        Page<RecipeDTO> recipes = this.recipeService.getRecipeByTittle(tittle, pageable);
        if (recipes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(recipes, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/users/{idUser}/recipes", produces = "application/json")
    public ResponseEntity<List<RecipeDTO>> getAllRecipesFromUser(@PathVariable Long idUser) {
        List<RecipeDTO> recipes = this.recipeService.getAllRecipesFromUser(idUser);
        if (!recipes.isEmpty()) {
            return new ResponseEntity<>(recipes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/users/{id}/recipes", produces = "application/json", consumes = "application/json")
    public ResponseEntity<RecipeDTO> saveRecipe(@PathVariable Long id, @RequestBody RecipeDTO recipeDTO, Principal principal) {
        String username = principal.getName();
        if (username.equals(this.userService.getUserById(id).get().getEmail())) {
            RecipeDTO savedRecipe = this.recipeService.saveRecipe(recipeDTO, username);
            return new ResponseEntity<>(savedRecipe, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping(value = "/users/{userId}/recipes/{id}/remove", produces = "application/json")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long userId, @PathVariable Long id, Principal principal) {
        Optional<UserDTO> optionalUser = this.userService.getByEmail(principal.getName());
        boolean isAdmin = optionalUser.get().getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"));
        if (principal.getName().equals(this.userService.getUserById(userId).get().getEmail()) || isAdmin) {
            this.recipeService.deleteRecipe(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


    @PutMapping(value = "/users/{userId}/recipes/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<RecipeDTO> updateRecipe(@PathVariable Long userId, @PathVariable Long id, @RequestBody RecipeDTO recipeDTO, Principal principal) {
        if (principal.getName().equals(this.userService.getUserById(userId).get().getEmail())) {
            RecipeDTO updatedRecipe = this.recipeService.updateRecipe(id, recipeDTO);
            return new ResponseEntity<>(updatedRecipe, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(value = "/users/{userId}/recipes/{id}", produces = "application/json")
    public ResponseEntity<RecipeDTO> getRecipeByIdFromUser(@PathVariable Long userId, @PathVariable Long id, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (principal.getName().equals(this.userService.getUserById(userId).get().getEmail())) {
            Optional<RecipeDTO> recipe = this.recipeService.getRecipeByIdFromUser(userId, id);
            return recipe.map(recipeDTO -> new ResponseEntity<>(recipeDTO, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }







}
