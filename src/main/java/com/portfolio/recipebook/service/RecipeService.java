package com.portfolio.recipebook.service;

import com.portfolio.recipebook.command.RecipeCommand;
import com.portfolio.recipebook.dto.RecipeDto;

import java.util.List;

public interface RecipeService{
    List<RecipeDto> getAll();
    RecipeDto create(RecipeCommand recipeCommand);
    RecipeDto update(RecipeCommand recipeCommand);
    void delete(Long id);

    RecipeDto getById(Long recipeId);
}
