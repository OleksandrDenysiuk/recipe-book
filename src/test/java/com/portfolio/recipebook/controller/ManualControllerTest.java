package com.portfolio.recipebook.controller;

import com.portfolio.recipebook.model.Recipe;
import com.portfolio.recipebook.service.ImageService;
import com.portfolio.recipebook.service.RecipeService;
import com.portfolio.recipebook.service.StepService;
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
class ManualControllerTest {
    @Mock
    StepService stepService;
    @Mock
    ImageService imageService;
    @Mock
    RecipeService recipeService;

    @InjectMocks
    ManualController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void viewManual() throws Exception {
        Recipe recipe = Recipe.builder().id(1L).build();

        when(recipeService.findById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/1/manual"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attributeExists("step"))
                .andExpect(view().name("manual/indexAndFromStep"));

        verify(recipeService,times(1)).findById(anyLong());
    }

    @Test
    void createStep() {

    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(get("/recipe/1/manual/step/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/manual"));

        verify(stepService,times(1)).deleteOne(anyLong(),anyLong());
    }

    @Test
    void edit() {
    }
}