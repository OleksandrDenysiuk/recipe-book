package com.portfolio.recipebook.service;

import com.portfolio.recipebook.model.Ingredient;

public interface IngredientService{
    Ingredient findByIngredientIdAndRecipeId(Long ingredientId, Long recipeId);
    void deleteById(Long ingredientId, Long recipeId);
    Ingredient save(Ingredient ingredient, Long recipeId);
}
