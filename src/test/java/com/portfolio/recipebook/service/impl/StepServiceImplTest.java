package com.portfolio.recipebook.service.impl;

import com.portfolio.recipebook.command.StepCommand;
import com.portfolio.recipebook.dto.StepDto;
import com.portfolio.recipebook.model.Recipe;
import com.portfolio.recipebook.model.Step;
import com.portfolio.recipebook.repository.RecipeRepository;
import com.portfolio.recipebook.repository.StepRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StepServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    StepRepository stepRepository;

    @InjectMocks
    StepServiceImpl stepService;

    @Test
    void getAll(){
        Long recipeId = 1L;
        Recipe recipe = Recipe.builder().id(recipeId).build();
        Step step1 = Step.builder().id(1L).build();
        step1.setRecipe(recipe);
        Step step2 = Step.builder().id(2L).build();
        step1.setRecipe(recipe);
        recipe.getSteps().add(step1);
        recipe.getSteps().add(step2);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        List<StepDto> stepDtoList = stepService.getAllByRecipeId(recipeId);

        assertEquals(2, stepDtoList.size());
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    void getOneByIdAndRecipeId() {
        Long stepId = 1L;
        Long recipeId = 1L;
        Recipe recipe = Recipe.builder().id(recipeId).build();
        Step step = Step.builder().id(stepId).build();
        step.setRecipe(recipe);
        recipe.getSteps().add(step);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        StepDto stepDto = stepService.getOneByIdAndRecipeId(stepId, recipe.getId());

        assertNotNull(stepDto);
        assertEquals(stepId, step.getId());
        verify(recipeRepository,times(1)).findById(anyLong());
    }


    @Test
    void create() {
        Recipe recipe = Recipe.builder().id(1L).build();

        StepCommand stepCommand = new StepCommand();
        stepCommand.setDescription("description");
        stepCommand.setImage(getFile());
        Step step = Step.builder().id(1L).build();
        step.setRecipe(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(stepRepository.save(any())).thenReturn(step);

        StepDto stepDto = stepService.create(stepCommand, recipe.getId());

        assertNotNull(stepDto);
        assertEquals(1L,stepDto.getId());
        verify(recipeRepository,times(1)).findById(anyLong());
        verify(stepRepository,times(1)).save(any());
    }

    @Test
    void update() {
        Long stepId = 1L;
        Long recipeId = 1L;
        Recipe recipe = Recipe.builder().id(recipeId).build();
        Step step = Step.builder()
                .id(stepId)
                .description("description")
                .build();
        step.setRecipe(recipe);
        recipe.getSteps().add(step);

        StepCommand stepCommand = new StepCommand();
        stepCommand.setId(stepId);
        stepCommand.setDescription("description2");
        stepCommand.setImage(getFile());

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(stepRepository.save(any())).thenReturn(step);

        StepDto stepDto = stepService.update(stepCommand, recipeId);

        assertNotNull(stepDto);
        assertEquals(1L, stepDto.getId());
        assertEquals("description2",stepDto.getDescription());
        verify(recipeRepository,times(1)).findById(anyLong());
        verify(stepRepository,times(1)).save(any());
    }

    @Test
    void delete() {
        Long stepId = 1L;
        Long recipeId = 1L;
        Recipe recipe = Recipe.builder().id(recipeId).build();
        Step step = Step.builder().id(stepId).build();
        step.setRecipe(recipe);
        recipe.getSteps().add(step);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        stepService.delete(stepId, recipeId);

        assertEquals(0,recipe.getSteps().size());
        assertNull(step.getRecipe());
        verify(recipeRepository,times(1)).findById(anyLong());
        verify(stepRepository,times(1)).delete(any());
    }

    private MultipartFile getFile(){
        return  new MultipartFile() {
            @Override
            public String getName() {
                return "image";
            }

            @Override
            public String getOriginalFilename() {
                return null;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File file) throws IOException, IllegalStateException {

            }
        };
    }
}