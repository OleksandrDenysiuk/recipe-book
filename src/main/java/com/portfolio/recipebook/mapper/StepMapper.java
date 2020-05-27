package com.portfolio.recipebook.mapper;

import com.portfolio.recipebook.dto.StepDto;
import com.portfolio.recipebook.model.Step;

public class StepMapper {
    public static StepDto toDto(Step step){
        StepDto dto = new StepDto();
        dto.setId(step.getId());
        dto.setDescription(step.getDescription());
        dto.setImage(step.getImage());
        dto.setRecipeId(step.getRecipe().getId());
        return dto;
    }
}
