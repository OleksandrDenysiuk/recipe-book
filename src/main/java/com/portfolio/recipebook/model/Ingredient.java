package com.portfolio.recipebook.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ingredients")
public class Ingredient extends BaseEntity{

    @Builder
    public Ingredient(Long id, String name, int amount) {
        super(id);
        this.name = name;
        this.amount = amount;
    }

    @NotBlank
    @Size(max = 34)
    private String name;

    @Min(1)
    @Max(50)
    private int amount;

    @ManyToOne
    @JoinColumn(name = "fk_recipe")
    private Recipe recipe;

    @Enumerated(EnumType.STRING)
    private MeasureOfIngredient measure;
}
