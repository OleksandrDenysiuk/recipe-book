package com.portfolio.recipebook.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recipes")
public class Recipe extends BaseEntity{

    @Builder
    public Recipe(Long id, String title, String description,int cookTime) {
        super(id);
        this.title = title;
        this.description = description;
        this.cookTime = cookTime;
    }

    private String title;

    private String description;

    private int cookTime;

    @OneToMany(cascade = CascadeType.ALL, mappedBy =  "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    @Lob
    private Byte[] image;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    Set<Step> steps = new HashSet<>();

    public Recipe addIngredient(Ingredient ingredient){
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }

    public Recipe addStep(Step step){
        step.setRecipe(this);
        this.steps.add(step);
        return this;
    }
}
