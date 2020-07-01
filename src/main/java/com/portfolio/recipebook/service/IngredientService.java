package com.portfolio.recipebook.service;

import com.portfolio.recipebook.command.IngredientCommand;
import com.portfolio.recipebook.dto.IngredientDto;

import java.util.List;

public interface IngredientService{
    List<IngredientDto> getAllByRecipeId(Long recipeId);

    IngredientDto create(IngredientCommand ingredientCommand, Long recipeId);

    void delete(Long ingredientId, Long recipeId);
}
