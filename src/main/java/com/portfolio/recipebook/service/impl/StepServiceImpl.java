package com.portfolio.recipebook.service.impl;

import com.portfolio.recipebook.command.StepCommand;
import com.portfolio.recipebook.dto.StepDto;
import com.portfolio.recipebook.exception.EntityNotFoundException;
import com.portfolio.recipebook.mapper.ImageMapper;
import com.portfolio.recipebook.mapper.StepMapper;
import com.portfolio.recipebook.model.Recipe;
import com.portfolio.recipebook.model.Step;
import com.portfolio.recipebook.repository.RecipeRepository;
import com.portfolio.recipebook.repository.StepRepository;
import com.portfolio.recipebook.service.StepService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StepServiceImpl implements StepService {

    private final RecipeRepository recipeRepository;
    private final StepRepository stepRepository;

    public StepServiceImpl(RecipeRepository recipeRepository, StepRepository stepRepository) {
        this.recipeRepository = recipeRepository;
        this.stepRepository = stepRepository;
    }

    @Override
    @Transactional
    public StepDto create(StepCommand stepCommand, Long recipeId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();

            Step step = new Step();
            step.setId(stepCommand.getId());
            step.setDescription(stepCommand.getDescription());
            step.setRecipe(recipe);
            step.setImage(ImageMapper.toByteArray(stepCommand.getImage()));
            recipe.addStep(step);

            return StepMapper.toDto(stepRepository.save(step));
        } else {
            throw new EntityNotFoundException("Recipe", recipeId);

        }
    }

    @Override
    @Transactional
    public StepDto update(StepCommand stepCommand, Long recipeId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (recipeOptional.isEmpty()) {
            Recipe recipe = recipeOptional.get();
            Optional<Step> stepOptional = recipe.getSteps()
                    .stream()
                    .filter(step -> step.getId().equals(stepCommand.getId()))
                    .findFirst();
            if (stepOptional.isPresent()) {
                Step step = stepOptional.get();
                if (stepCommand.getImage().getSize() != 0) {
                    step.setImage(ImageMapper.toByteArray(stepCommand.getImage()));
                }
                step.setDescription(stepCommand.getDescription());
                return StepMapper.toDto(stepRepository.save(step));
            } else {
                throw new EntityNotFoundException("Step", stepCommand.getId());
            }
        } else {
            throw new EntityNotFoundException("Recipe", recipeId);
        }
    }

    @Override
    @Transactional
    public List<StepDto> getAll(Long recipeId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            return recipe.getSteps()
                    .stream()
                    .map(StepMapper::toDto)
                    .sorted(Comparator.comparing(StepDto::getId).reversed())
                    .collect(Collectors.toList());
        } else {
            throw new EntityNotFoundException("Recipe", recipeId);
        }
    }

    @Override
    @Transactional
    public StepDto getByIdAndRecipeId(Long stepId, Long recipeId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            Optional<Step> optionalStep = recipe.getSteps().stream()
                    .filter(step -> step.getId().equals(stepId))
                    .findFirst();
            if (optionalStep.isPresent()) {
                return StepMapper.toDto(optionalStep.get());
            } else {
                throw new EntityNotFoundException("Step", stepId);
            }
        } else {
            throw new EntityNotFoundException("Recipe", recipeId);
        }
    }

    @Override
    @Transactional
    public void delete(Long stepId, Long recipeId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            Optional<Step> stepOptional = recipe.getSteps()
                    .stream()
                    .filter(step -> step.getId().equals(stepId))
                    .findFirst();
            if (stepOptional.isEmpty()) {
                throw new EntityNotFoundException("Step", stepId);
            } else {
                Step step = stepOptional.get();
                recipe.getSteps().remove(step);
                stepRepository.delete(step);
            }
        } else {
            throw new EntityNotFoundException("Recipe", recipeId);
        }
    }
}
