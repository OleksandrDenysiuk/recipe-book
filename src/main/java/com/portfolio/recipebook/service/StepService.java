package com.portfolio.recipebook.service;

import com.portfolio.recipebook.command.StepCommand;
import com.portfolio.recipebook.dto.StepDto;

import java.util.List;

public interface StepService{
    List<StepDto> getAllByRecipeId(Long recipeId);

    StepDto getOneByIdAndRecipeId(Long stepId, Long recipeId);

    StepDto create(StepCommand stepCommand, Long recipeId);

    StepDto update(StepCommand stepCommand, Long recipeId);

    void delete(Long stepId, Long recipeId);
}
