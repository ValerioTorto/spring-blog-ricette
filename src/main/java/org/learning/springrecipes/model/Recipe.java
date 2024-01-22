package org.learning.springrecipes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity
@Table(name = "recipes")
public class Recipe {

    //ATTRIBUTI
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Title can't be blank")
    @Column(nullable = false)
    private String title;
    @NotEmpty(message = "Ingredients can't be blank")
    @Lob
    @Column(length = 1024)
    private String ingredients;

    private String photo;
    private String prepTime;
    private Integer portions;
    @Lob
    @Column(length = 65535)
    private String recipeText;
    @ManyToMany
    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }

    public Integer getPortions() {
        return portions;
    }

    public void setPortions(Integer portions) {
        this.portions = portions;
    }

    public String getRecipeText() {
        return recipeText;
    }

    public void setRecipeText(String recipeText) {
        this.recipeText = recipeText;
    }
}
