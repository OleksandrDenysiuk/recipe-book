package com.portfolio.recipebook.controller.api;

import com.portfolio.recipebook.dto.StepDto;
import com.portfolio.recipebook.service.StepService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class StepRestControllerTest {
    @Mock
    StepService stepService;
    @InjectMocks
    StepRestController stepRestController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(stepRestController).build();
    }


    @Test
    void getStepList() throws Exception {
        StepDto dto1 = new StepDto();
        dto1.setId(1L);
        StepDto dto2 = new StepDto();
        dto2.setId(2L);
        List<StepDto> dtoList = new ArrayList<>();
        dtoList.add(dto1);
        dtoList.add(dto2);

        when(stepService.getAllByRecipeId(anyLong())).thenReturn(dtoList);

        mockMvc.perform(get("/api/recipes/1/steps"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andReturn();

        verify(stepService, times(1)).getAllByRecipeId(anyLong());
    }

    @Test
    void getStep() throws Exception {
        StepDto dto = new StepDto();
        dto.setId(1L);

        when(stepService.getOneByIdAndRecipeId(anyLong(), anyLong())).thenReturn(dto);

        mockMvc.perform(get("/api/recipes/1/steps/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn();

        verify(stepService, times(1)).getOneByIdAndRecipeId(anyLong(), anyLong());
    }

    @Test
    void create() throws Exception {
        StepDto dto = new StepDto();
        dto.setId(1L);

        when(stepService.create(any(), anyLong())).thenReturn(dto);

        mockMvc.perform(multipart("/api/recipes/1/steps")
                .file("image", new byte[]{1, 1})
                .param("description", "description"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn();

        verify(stepService, times(1)).create(any(), anyLong());
    }

    @Test
    void update() throws Exception {
        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/api/recipes/1/steps/1");
        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });

        StepDto dto = new StepDto();
        dto.setId(1L);

        when(stepService.update(any(), anyLong())).thenReturn(dto);

        mockMvc.perform(builder
                .file("image", new byte[]{1, 1})
                .param("description", "description"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn();

        verify(stepService, times(1)).update(any(), anyLong());
    }

    @Test
    void deleteRecipe() throws Exception {
        mockMvc.perform(delete("/api/recipes/1/steps/1"))
                .andExpect(status().isOk());

        verify(stepService, times(1)).delete(anyLong(), anyLong());
    }
}