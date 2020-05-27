package com.portfolio.recipebook.mapper;

import com.portfolio.recipebook.dto.IngredientDto;
import com.portfolio.recipebook.model.Ingredient;

public class IngredientMapper {
    public static IngredientDto toDto(Ingredient ingredient){

        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId(ingredient.getId());
        ingredientDto.setName(ingredient.getName());
        ingredientDto.setAmount(ingredient.getAmount());
        ingredientDto.setMeasure(ingredient.getMeasure());
        ingredientDto.setRecipeId(ingredient.getRecipe().getId());

        return ingredientDto;
    }
}
