package com.rolanmunoz.whatscooking.application.dto;


import java.io.Serializable;

public class FavoriteDTO implements Serializable {

    private Long id;
    private Long userId;
    private Long recipeId;

    public FavoriteDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }
}
