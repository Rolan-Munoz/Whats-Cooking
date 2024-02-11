package com.rolanmunoz.whatscooking.application.dto;

import com.rolanmunoz.whatscooking.domain.entity.User;

import java.io.Serializable;
import java.util.List;

public class RecipeDTO implements Serializable {
    private Long id_recipe;
    private String tittle;
    private List<String> ingredients;
    private String description;
    private String instructions;
    private byte[] image;
    private byte[] video;
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public RecipeDTO() {
    }

    public Long getId() {
        return id_recipe;
    }

    public void setId(Long id_recipe) {
        this.id_recipe = id_recipe;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getVideo() {
        return video;
    }

    public void setVideo(byte[] video) {
        this.video = video;
    }

    public void setUser(User user) {
        this.userId = user.getId();
    }
}
