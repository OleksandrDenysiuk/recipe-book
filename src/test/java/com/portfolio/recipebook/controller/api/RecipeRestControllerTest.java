package com.portfolio.recipebook.controller.api;

import com.portfolio.recipebook.dto.RecipeDto;
import com.portfolio.recipebook.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RecipeRestControllerTest {
    @Mock
    RecipeService recipeService;
    @InjectMocks
    RecipeRestController recipeRestController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(recipeRestController).build();
    }


    @Test
    void getRecipeList() throws Exception {
        RecipeDto dto1 = new RecipeDto();
        dto1.setId(1L);
        RecipeDto dto2 = new RecipeDto();
        dto2.setId(2L);
        List<RecipeDto> dtoList = new ArrayList<>();
        dtoList.add(dto1);
        dtoList.add(dto2);

        when(recipeService.getAll()).thenReturn(dtoList);

        mockMvc.perform(get("/api/recipes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andReturn();

        verify(recipeService, times(1)).getAll();
    }

    @Test
    void getRecipe() throws Exception {
        RecipeDto dto1 = new RecipeDto();
        dto1.setId(1L);

        when(recipeService.getOneById(anyLong())).thenReturn(dto1);

        mockMvc.perform(get("/api/recipes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andReturn();

        verify(recipeService, times(1)).getOneById(anyLong());
    }

    @Test
    void create() throws Exception {
        RecipeDto dto = new RecipeDto();
        dto.setId(1L);

        when(recipeService.create(any())).thenReturn(dto);

        mockMvc.perform(multipart("/api/recipes")
                .file("image", new byte[]{1, 1})
                .param("title", "title")
                .param("description", "description")
                .param("cookTime", "1")
                .param("difficulty", "EASY"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn();

        verify(recipeService, times(1)).create(any());
    }

    @Test
    void update() throws Exception {
        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/api/recipes/1");
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        });

        RecipeDto dto = new RecipeDto();
        dto.setId(1L);

        when(recipeService.update(any())).thenReturn(dto);

        mockMvc.perform(builder
                .file("image", new byte[]{1, 1})
                .param("title", "title")
                .param("description", "description")
                .param("cookTime", "1")
                .param("difficulty", "EASY"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn();

        verify(recipeService, times(1)).update(any());
    }

    @Test
    void deleteRecipe() throws Exception {
        mockMvc.perform(delete("/api/recipes/1"))
                .andExpect(status().isOk());

        verify(recipeService,times(1)).delete(anyLong());
    }
}