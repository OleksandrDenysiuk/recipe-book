package com.portfolio.recipebook.controller.view;

import com.portfolio.recipebook.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ExceptionHandlerControllerTest {

    @Mock
    RecipeService recipeService;
    @InjectMocks
    RecipeController recipeController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
                .setControllerAdvice(new ExceptionHandlerController()).build();
    }

    @Test
    void handleNumberFormatException() throws Exception {
            mockMvc.perform(get("/recipes/id"))
            .andExpect(status().isBadRequest())
            .andExpect(model().attributeExists("exception"))
            .andExpect(view().name("error/400error"));
    }
}