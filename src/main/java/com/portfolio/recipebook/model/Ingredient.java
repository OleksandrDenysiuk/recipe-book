package com.portfolio.recipebook.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ingredients")
public class Ingredient extends BaseEntity{

    @NotBlank
    @Size(max = 34)
    private String name;

    @Min(1)
    @Max(50)
    private int amount;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @Enumerated(EnumType.STRING)
    private MeasureOfIngredient measure;
}
