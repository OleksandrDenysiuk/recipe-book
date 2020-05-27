package com.portfolio.recipebook.model;


import lombok.*;

import javax.persistence.*;

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

    private String name;

    private int amount;

    @ManyToOne
    @JoinColumn(name = "fk_recipe")
    private Recipe recipe;

    @Enumerated(EnumType.STRING)
    private MeasureOfIngredient measure;
}
