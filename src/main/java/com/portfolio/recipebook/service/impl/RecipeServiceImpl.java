package com.portfolio.recipebook.service.impl;

import com.portfolio.recipebook.command.RecipeCommand;
import com.portfolio.recipebook.dto.RecipeDto;
import com.portfolio.recipebook.mapper.ImageMapper;
import com.portfolio.recipebook.mapper.RecipeMapper;
import com.portfolio.recipebook.model.Difficulty;
import com.portfolio.recipebook.model.Recipe;
import com.portfolio.recipebook.repository.RecipeRepository;
import com.portfolio.recipebook.service.RecipeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public RecipeDto create(RecipeCommand recipeCommand) {
        Recipe recipe = new Recipe();
        recipe.setId(recipeCommand.getId());
        recipe.setCookTime(recipeCommand.getCookTime());
        recipe.setDescription(recipeCommand.getDescription());
        recipe.setTitle(recipeCommand.getTitle());
        recipe.setDifficulty(Difficulty.valueOf(recipeCommand.getDifficulty()));
        recipe.setImage(ImageMapper.toByteArray(recipeCommand.getImage()));
        return RecipeMapper.toDto(recipeRepository.save(recipe));
    }

    @Override
    public RecipeDto update(RecipeCommand recipeCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeCommand.getId());
        if(recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            recipe.setCookTime(recipeCommand.getCookTime());
            recipe.setDescription(recipeCommand.getDescription());
            recipe.setTitle(recipeCommand.getTitle());
            recipe.setDifficulty(Difficulty.valueOf(recipeCommand.getDifficulty()));
            if (recipeCommand.getImage() != null) {
                recipe.setImage(ImageMapper.toByteArray(recipeCommand.getImage()));
            }

            return RecipeMapper.toDto(recipeRepository.save(recipe));
        }else{
            throw  new RuntimeException("Recipe not found");
        }
    }

    @Override
    public void delete(Long recipeId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if(recipeOptional.isPresent()) {
            recipeRepository.delete(recipeOptional.get());
        }else{
            throw  new RuntimeException("Recipe not found");
        }
    }

    @Override
    @Transactional
    public RecipeDto getById(Long recipeId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if(recipeOptional.isPresent()) {
            return RecipeMapper.toDto(recipeOptional.get());
        }else{
            throw  new RuntimeException("Recipe not found");
        }
    }

}
