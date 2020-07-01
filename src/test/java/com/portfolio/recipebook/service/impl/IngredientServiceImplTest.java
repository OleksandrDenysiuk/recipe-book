package com.portfolio.recipebook.service.impl;

import com.portfolio.recipebook.command.IngredientCommand;
import com.portfolio.recipebook.dto.IngredientDto;
import com.portfolio.recipebook.model.Ingredient;
import com.portfolio.recipebook.model.Recipe;
import com.portfolio.recipebook.repository.IngredientRepository;
import com.portfolio.recipebook.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    IngredientRepository ingredientRepository;

    @InjectMocks
    IngredientServiceImpl ingredientService;

    @Test
    void getAllByRecipeId(){
        Recipe recipe = Recipe.builder().id(1L).build();
        Ingredient ingredient1 = Ingredient.builder().id(1L).build();
        ingredient1.setRecipe(recipe);
        Ingredient ingredient2 = Ingredient.builder().id(2L).build();
        ingredient2.setRecipe(recipe);
        recipe.getIngredients().add(ingredient1);
        recipe.getIngredients().add(ingredient2);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        List<IngredientDto> ingredientDtoList = ingredientService.getAllByRecipeId(1L);

        assertEquals(2, ingredientDtoList.size());

        verify(recipeRepository,times(1)).findById(anyLong());
    }

    @Test
    void create() {
        Recipe recipe = Recipe.builder().id(1L).build();
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setName("name");
        ingredientCommand.setAmount(2);
        ingredientCommand.setMeasure("TEASPOON");
        Ingredient ingredient = Ingredient.builder().id(1L).build();
        ingredient.setRecipe(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(ingredientRepository.save(any())).thenReturn(ingredient);

        IngredientDto ingredientDto = ingredientService.create(ingredientCommand, 1L);

        assertNotNull(ingredientDto);
        assertEquals(1,recipe.getIngredients().size());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(ingredientRepository, times(1)).save(any());
    }

    @Test
    void delete() {
        Recipe recipe = Recipe.builder().id(1L).build();
        Ingredient ingredient = Ingredient.builder().id(1L).build();
        ingredient.setRecipe(recipe);
        recipe.addIngredient(ingredient);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        ingredientService.delete(1L,1L);

        assertNull(ingredient.getRecipe());
        assertEquals(0,recipe.getIngredients().size());
    }
}