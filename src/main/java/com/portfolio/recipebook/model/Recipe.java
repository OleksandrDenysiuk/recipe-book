package com.portfolio.recipebook.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    @NotBlank
    @Size(max = 34)
    private String title;

    @NotBlank
    @Lob
    @Type(type = "text")
    @Size(max = 350)
    private String description;

    @Min(1)
    @Max(360)
    private int cookTime;

    @OneToMany(cascade = CascadeType.ALL, mappedBy =  "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    @Lob
    private Byte[] image;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Manual manual;

    public Recipe addIngredient(Ingredient ingredient){
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }
}
