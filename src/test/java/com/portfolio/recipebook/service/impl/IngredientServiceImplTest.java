package com.portfolio.recipebook.service.impl;

import com.portfolio.recipebook.model.Ingredient;
import com.portfolio.recipebook.model.Recipe;
import com.portfolio.recipebook.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    @InjectMocks
    IngredientServiceImpl ingredientService;


    @BeforeEach
    void setUp() {
    }

    @Test
    void findByIngredientIdAndRecipeId() {
        Recipe recipe = Recipe.builder().id(1L).build();

        Long recipeId = recipe.getId();
        Long ingredientId = 1L;

        recipe.addIngredient(Ingredient.builder().id(1L).build());

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        Ingredient ingredient = ingredientService
                .findOne(ingredientId, recipeId);

        assertNotNull(ingredient);

        verify(recipeRepository,times(1)).findById(anyLong());
        verify(recipeRepository,never()).findAll();

        assertEquals(ingredient.getId(),ingredientId);
    }

    @Test
    void save() {
        Recipe recipe = Recipe.builder().id(1L).build();

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        ingredientService.save(Ingredient.builder().id(1L).build(), 1L);

        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));

        assertEquals(1,recipe.getIngredients().size());
    }

    @Test
    void deleteById() {
        Recipe recipe = Recipe.builder().id(1L).build();
        recipe.addIngredient(Ingredient.builder().id(1L).build());

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        ingredientService.deleteOne(1L,1L);

        assertEquals(0,recipe.getIngredients().size());
    }
}