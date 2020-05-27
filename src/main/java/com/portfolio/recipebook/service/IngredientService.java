package com.portfolio.recipebook.service;

import com.portfolio.recipebook.command.IngredientCommand;
import com.portfolio.recipebook.dto.IngredientDto;
import com.portfolio.recipebook.model.Ingredient;

import java.util.List;

public interface IngredientService{
    List<IngredientDto> getAll(Long recipeId);

    IngredientDto create(IngredientCommand ingredientCommand, Long recipeId);

    Ingredient findOne(Long ingredientId, Long recipeId);

    void deleteOne(Long ingredientId, Long recipeId);
}
