package com.portfolio.recipebook.service;

import com.portfolio.recipebook.command.RecipeCommand;
import com.portfolio.recipebook.dto.RecipeDto;

import java.io.IOException;
import java.util.List;

public interface RecipeService{
    RecipeDto create(RecipeCommand recipeCommand) throws IOException;

    RecipeDto update(RecipeCommand recipeCommand);

    void delete(Long id);

    RecipeDto getById(Long recipeId);

    List<RecipeDto> getAll();
}
