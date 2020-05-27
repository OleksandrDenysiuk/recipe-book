package com.portfolio.recipebook.dto;

import com.portfolio.recipebook.model.MeasureOfIngredient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@NoArgsConstructor
public class IngredientDto {

    private Long id;

    private String name;

    private int amount;

    private Long recipeId;

    @Enumerated(EnumType.STRING)
    private MeasureOfIngredient measure;
}
