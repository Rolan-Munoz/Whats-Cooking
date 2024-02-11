package com.rolanmunoz.whatscooking.application.dto;


import com.rolanmunoz.whatscooking.domain.entity.Role;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

public class UserDTO implements Serializable {

    private Long id;
    private String name;
    private String email;
    private String password;
    private byte[] photo;
    private List<RoleDTO> roles;
    private List<RecipeDTO> recipes;
    private List<FavoriteDTO> favorites;



    public UserDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }

    public List<RecipeDTO> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<RecipeDTO> recipes) {
        this.recipes = recipes;
    }

    public List<FavoriteDTO> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<FavoriteDTO> favorites) {
        this.favorites = favorites;
    }
}
