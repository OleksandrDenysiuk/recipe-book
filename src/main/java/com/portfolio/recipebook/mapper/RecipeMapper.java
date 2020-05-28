package com.portfolio.recipebook.mapper;

import com.portfolio.recipebook.dto.RecipeDto;
import com.portfolio.recipebook.model.Recipe;

public class RecipeMapper {
    public static RecipeDto toDto(Recipe recipe){
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(recipe.getId());
        recipeDto.setCookTime(recipe.getCookTime());
        recipeDto.setTitle(recipe.getTitle());
        recipeDto.setDescription(recipe.getDescription());
        recipeDto.setDifficulty(recipe.getDifficulty());
        recipeDto.setImage(recipe.getImage());
        recipe.getIngredients().forEach(ingredient -> recipeDto.getIngredients().add(IngredientMapper.toDto(ingredient)));
        recipe.getSteps().forEach(step -> recipeDto.getSteps().add(StepMapper.toDto(step)));
        return recipeDto;
    }
}
