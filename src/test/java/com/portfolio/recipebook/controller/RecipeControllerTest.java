package com.portfolio.recipebook.controller;

import com.portfolio.recipebook.model.Recipe;
import com.portfolio.recipebook.service.ImageService;
import com.portfolio.recipebook.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    ImageService imageService;

    @InjectMocks
    RecipeController recipeController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    void viewList() throws Exception {
        mockMvc.perform(get("/recipe/all"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipes"))
                .andExpect(view().name("recipe/list"));

        verify(recipeService, times(1)).findAll();
    }

    @Test
    void viewDetails() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeService.findById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/index"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findById(anyLong());
    }

    @Test
    void viewForm() throws Exception {
        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/form"));
    }

    @Test
    void editForm() throws Exception {
        Recipe recipe = Recipe.builder().id(1L).build();

        when(recipeService.findById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/1/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/form"));
    }

    @Test
    void createOrUpdate() throws Exception {
        //todo multipart request
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/all"));
    }
}