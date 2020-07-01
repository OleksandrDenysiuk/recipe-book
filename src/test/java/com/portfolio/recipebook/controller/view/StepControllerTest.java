package com.portfolio.recipebook.controller.view;

import com.portfolio.recipebook.service.StepService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class StepControllerTest {
    @Mock
    StepService stepService;
    @InjectMocks
    StepController stepController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(stepController).build();
    }

    @Test
    void getStepsPage() throws Exception {
        when(stepService.getAllByRecipeId(anyLong())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/recipes/1/steps"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("steps"))
                .andExpect(view().name("step/listForm"));

        verify(stepService,times(1)).getAllByRecipeId(anyLong());
    }
}