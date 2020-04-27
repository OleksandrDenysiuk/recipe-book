package com.portfolio.recipebook.service;

import com.portfolio.recipebook.model.Ingredient;

public interface IngredientService{
    Ingredient findOne(Long ingredientId, Long recipeId);
    void deleteOne(Long ingredientId, Long recipeId);
    Ingredient save(Ingredient ingredient, Long recipeId);
}
