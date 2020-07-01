package com.portfolio.recipebook.controller.view;

import com.portfolio.recipebook.dto.RecipeDto;
import com.portfolio.recipebook.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    @Mock
    RecipeService recipeService;
    @InjectMocks
    RecipeController recipeController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    void recipeList() throws Exception {
        when(recipeService.getAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/recipes"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipes"))
                .andExpect(view().name("recipe/list"));

        verify(recipeService,times(1)).getAll();
    }

    @Test
    void recipeIndex() throws Exception {
        when(recipeService.getById(anyLong())).thenReturn(new RecipeDto());

        mockMvc.perform(get("/recipes/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/index"));

        verify(recipeService,times(1)).getById(anyLong());
    }

    @Test
    void recipeEditForm() throws Exception {
        when(recipeService.getById(anyLong())).thenReturn(new RecipeDto());

        mockMvc.perform(get("/recipes/1/form"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/form"));

        verify(recipeService,times(1)).getById(anyLong());
    }

    @Test
    void recipeCreateForm() throws Exception {

        mockMvc.perform(get("/recipes/form"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/form"));

    }
}