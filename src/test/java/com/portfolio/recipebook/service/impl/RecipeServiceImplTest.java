package com.portfolio.recipebook.service.impl;

import com.portfolio.recipebook.model.Recipe;
import com.portfolio.recipebook.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    @InjectMocks
    RecipeServiceImpl recipeService;

    @BeforeEach
    void setUp(){
    }

    @Test
    void findAll() {
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(Recipe.builder().id(2L).build());
        recipes.add(Recipe.builder().id(3L).build());

        when(recipeRepository.findAll()).thenReturn(recipes);

        Set<Recipe> recipeSet = recipeService.findAll();

        assertEquals(2, recipeSet.size());
        verify(recipeRepository,times(1)).findAll();
    }

    @Test
    void findById() {
        Optional<Recipe> recipe = Optional.of(Recipe.builder().id(1L).build());

        when(recipeRepository.findById(anyLong())).thenReturn(recipe);

        Recipe recipeFind = recipeService.findById(1L);

        assertNotNull(recipeFind);
        verify(recipeRepository,times(1)).findById(anyLong());
    }

    @Test
    void save() {
    Recipe recipe = Recipe.builder().id(1L).build();

    when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

    Recipe recipeSaved = recipeService.save(Recipe.builder().build());

    assertNotNull(recipeSaved);
    verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void deleteById() {
        Long recipeId = 1L;

        recipeService.deleteById(recipeId);

        verify(recipeRepository,times(1)).deleteById(anyLong());
    }
}