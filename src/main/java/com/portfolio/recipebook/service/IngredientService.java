package com.portfolio.recipebook.service;

import com.portfolio.recipebook.dto.IngredientDto;
import com.portfolio.recipebook.model.Ingredient;

import java.util.List;

public interface IngredientService{
    List<IngredientDto> getAll(Long recipeId);

    Ingredient findOne(Long ingredientId, Long recipeId);

    void deleteOne(Long ingredientId, Long recipeId);

    Ingredient save(Ingredient ingredient, Long recipeId);
}
