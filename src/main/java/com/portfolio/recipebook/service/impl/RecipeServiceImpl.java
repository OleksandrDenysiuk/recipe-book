package com.portfolio.recipebook.service.impl;

import com.portfolio.recipebook.command.RecipeCommand;
import com.portfolio.recipebook.dto.RecipeDto;
import com.portfolio.recipebook.exception.EntityNotFoundException;
import com.portfolio.recipebook.mapper.ImageMapper;
import com.portfolio.recipebook.mapper.RecipeMapper;
import com.portfolio.recipebook.model.Difficulty;
import com.portfolio.recipebook.model.Recipe;
import com.portfolio.recipebook.repository.RecipeRepository;
import com.portfolio.recipebook.service.RecipeService;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public List<RecipeDto> getAll() {
        List<RecipeDto> recipeDtoList = new ArrayList<>();
        recipeRepository.findAll().forEach(recipe -> recipeDtoList.add(RecipeMapper.toDto(recipe)));
        return recipeDtoList
                .stream()
                .sorted(Comparator.comparing(RecipeDto::getId).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public RecipeDto create(RecipeCommand recipeCommand) throws IOException {
        Recipe recipe = new Recipe();
        recipe.setId(recipeCommand.getId());
        recipe.setCookTime(recipeCommand.getCookTime());
        recipe.setDescription(recipeCommand.getDescription());
        recipe.setTitle(recipeCommand.getTitle());
        recipe.setDifficulty(Difficulty.valueOf(recipeCommand.getDifficulty()));
        if (recipeCommand.getImage().getSize() == 0) {
            MultipartFile multipartFile = new MockMultipartFile("defaultImage",
                    new FileInputStream(new File("src/main/resources/static/img/empty.png"))
            );
            recipe.setImage(ImageMapper.toByteArray(multipartFile));
        }
        return RecipeMapper.toDto(recipeRepository.save(recipe));
    }

    @Override
    @Transactional
    public RecipeDto update(RecipeCommand recipeCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeCommand.getId());
        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            recipe.setCookTime(recipeCommand.getCookTime());
            recipe.setDescription(recipeCommand.getDescription());
            recipe.setTitle(recipeCommand.getTitle());
            recipe.setDifficulty(Difficulty.valueOf(recipeCommand.getDifficulty()));
            if (recipeCommand.getImage().getSize() != 0) {
                recipe.setImage(ImageMapper.toByteArray(recipeCommand.getImage()));
            }
            return RecipeMapper.toDto(recipeRepository.save(recipe));
        } else {
            throw new EntityNotFoundException("Recipe", recipeCommand.getId());
        }
    }

    @Override
    public void delete(Long recipeId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (recipeOptional.isPresent()) {
            recipeRepository.delete(recipeOptional.get());
        } else {
            throw new EntityNotFoundException("Recipe", recipeId);
        }
    }

    @Override
    @Transactional
    public RecipeDto getById(Long recipeId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (recipeOptional.isPresent()) {
            return RecipeMapper.toDto(recipeOptional.get());
        } else {
            throw new EntityNotFoundException("Recipe", recipeId);
        }
    }

}
