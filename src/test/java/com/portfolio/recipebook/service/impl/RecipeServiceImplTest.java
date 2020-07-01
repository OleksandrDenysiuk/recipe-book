package com.portfolio.recipebook.service.impl;

import com.portfolio.recipebook.command.RecipeCommand;
import com.portfolio.recipebook.dto.RecipeDto;
import com.portfolio.recipebook.model.Difficulty;
import com.portfolio.recipebook.model.Recipe;
import com.portfolio.recipebook.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
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

    @Test
    void getAll() {
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(Recipe.builder().id(2L).build());
        recipes.add(Recipe.builder().id(3L).build());

        when(recipeRepository.findAll()).thenReturn(recipes);

        List<RecipeDto> recipeDtoList = recipeService.getAll();

        assertEquals(2, recipeDtoList.size());
        verify(recipeRepository,times(1)).findAll();
    }

    @Test
    void getOneById() {
        Optional<Recipe> recipe = Optional.of(Recipe.builder().id(1L).build());

        when(recipeRepository.findById(anyLong())).thenReturn(recipe);

        RecipeDto recipeDto = recipeService.getOneById(1L);

        assertNotNull(recipeDto);
        assertEquals(1, recipeDto.getId());
        verify(recipeRepository,times(1)).findById(anyLong());
    }

    @Test
    void create() throws IOException {
    Recipe recipe = Recipe.builder().id(1L).build();
    RecipeCommand recipeCommand = new RecipeCommand();
    recipeCommand.setCookTime(10);
    recipeCommand.setDescription("description");
    recipeCommand.setTitle("title");
    recipeCommand.setImage(getFile());
    recipeCommand.setDifficulty("HARD");

    when(recipeRepository.save(any())).thenReturn(recipe);

    RecipeDto recipeDto = recipeService.create(recipeCommand);

    assertNotNull(recipeDto);
    assertEquals(1, recipeDto.getId());
    verify(recipeRepository, times(1)).save(any());
    }

    @Test
    void update() throws IOException {
    Recipe recipe = Recipe.builder()
            .id(1L)
            .cookTime(1)
            .description("description")
            .title("title")
            .build();
    RecipeCommand recipeCommand = new RecipeCommand();
    recipeCommand.setId(1L);
    recipeCommand.setCookTime(10);
    recipeCommand.setDescription("description2");
    recipeCommand.setTitle("title2");
    recipeCommand.setImage(getFile());
    recipeCommand.setDifficulty("EASY");

    when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
    when(recipeRepository.save(any())).thenReturn(recipe);

    RecipeDto recipeDto = recipeService.update(recipeCommand);

    assertNotNull(recipeDto);
    assertEquals(1, recipeDto.getId());
    assertEquals("description2", recipeDto.getDescription());
    assertEquals("title2", recipeDto.getTitle());
    assertEquals(Difficulty.EASY, recipeDto.getDifficulty());
    verify(recipeRepository, times(1)).save(any());
    }

    @Test
    void delete() {
        Long recipeId = 1L;
        Recipe recipe = Recipe.builder().id(recipeId).build();
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        recipeService.delete(recipeId);

        verify(recipeRepository,times(1)).delete(any());
    }

    private MultipartFile getFile(){
        return new MultipartFile() {
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