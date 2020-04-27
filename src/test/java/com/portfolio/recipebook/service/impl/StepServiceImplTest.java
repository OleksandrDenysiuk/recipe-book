package com.portfolio.recipebook.service.impl;

import com.portfolio.recipebook.model.Recipe;
import com.portfolio.recipebook.model.Step;
import com.portfolio.recipebook.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StepServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    @InjectMocks
    StepServiceImpl stepService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findOne() {
        Long stepId = 1L;
        Recipe recipe = Recipe.builder().id(1L).build();
        recipe.getSteps().add(Step.builder().id(stepId).build());

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Step step = stepService.findOne(stepId, recipe.getId());

        assertEquals(stepId, step.getId());
        verify(recipeRepository,times(1)).findById(anyLong());
    }

    @Test
    void deleteOne() {
        Long stepId = 1L;
        Recipe recipe = Recipe.builder().id(1L).build();
        recipe.getSteps().add(Step.builder().id(stepId).build());

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        stepService.deleteOne(stepId, recipe.getId());

        verify(recipeRepository,times(1)).findById(anyLong());
        verify(recipeRepository,times(1)).save(any(Recipe.class));

        assertEquals(0,recipe.getSteps().size());
    }

    @Test
    void save() {
        Step stepToSave = Step.builder().id(1L).build();

        Recipe recipe = Recipe.builder().id(1L).build();

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Step step = stepService.save(stepToSave,recipe.getId());

        assertEquals(1L,step.getId());
    }
}