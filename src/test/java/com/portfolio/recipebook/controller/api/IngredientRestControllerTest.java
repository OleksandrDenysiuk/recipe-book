package com.portfolio.recipebook.controller.api;

import com.google.gson.Gson;
import com.portfolio.recipebook.command.IngredientCommand;
import com.portfolio.recipebook.dto.IngredientDto;
import com.portfolio.recipebook.service.IngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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
class IngredientRestControllerTest {

    @Mock
    IngredientService ingredientService;
    @InjectMocks
    IngredientRestController ingredientRestController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientRestController).build();
    }

    @Test
    void getIngredientList() throws Exception {
        IngredientDto dto1 = new IngredientDto();
        dto1.setId(1L);
        IngredientDto dto2 = new IngredientDto();
        dto2.setId(2L);
        List<IngredientDto> dtoList = new ArrayList<>();
        dtoList.add(dto1);
        dtoList.add(dto2);

        when(ingredientService.getAllByRecipeId(anyLong())).thenReturn(dtoList);

        mockMvc.perform(get("/api/recipes/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andReturn();

        verify(ingredientService, times(1)).getAllByRecipeId(anyLong());
    }

    @Test
    void create() throws Exception {
        IngredientDto dto = new IngredientDto();
        dto.setId(1L);

        IngredientCommand command = new IngredientCommand();
        command.setName("name");
        command.setAmount(2);
        command.setMeasure("DASH");

        when(ingredientService.create(any(), anyLong())).thenReturn(dto);

        mockMvc.perform(post("/api/recipes/1/ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn();
    }

    @Test
    void deleteIngredient() throws Exception {
        mockMvc.perform(delete("/api/recipes/1/ingredients/1"))
                .andExpect(status().isOk());

        verify(ingredientService, times(1)).delete(anyLong(), anyLong());
    }
}