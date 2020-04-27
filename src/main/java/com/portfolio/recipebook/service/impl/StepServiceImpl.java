package com.portfolio.recipebook.service.impl;

import com.portfolio.recipebook.model.Recipe;
import com.portfolio.recipebook.model.Step;
import com.portfolio.recipebook.repository.RecipeRepository;
import com.portfolio.recipebook.service.StepService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class StepServiceImpl implements StepService {

    private final RecipeRepository recipeRepository;

    public StepServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public Step findOne(Long stepId, Long recipeId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if(recipeOptional.isEmpty()){
            throw  new RuntimeException("Recipe was not founded with id: " + recipeId);
        }else{
            Recipe recipe = recipeOptional.get();
            Optional<Step> stepOptional = recipe.getSteps()
                    .stream()
                    .filter(step -> step.getId().equals(stepId))
                    .findFirst();
            if(stepOptional.isEmpty()){
                throw new RuntimeException("Recipe was not founded with id: " + recipeId);
            }else{
                return stepOptional.get();
            }
        }
    }

    @Override
    public void deleteOne(Long stepId, Long recipeId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if(recipeOptional.isEmpty()){
            throw  new RuntimeException("Recipe was not founded with id: " + recipeId);
        }else{
            Recipe recipe = recipeOptional.get();
            Optional<Step> stepOptional = recipe.getSteps()
                    .stream()
                    .filter(step -> step.getId().equals(stepId))
                    .findFirst();
            if(stepOptional.isEmpty()){
                throw new RuntimeException("Recipe was not founded with id: " + recipeId);
            }else{
                stepOptional.get().setRecipe(null);
                recipe.getSteps().remove(stepOptional.get());
                recipeRepository.save(recipe);
            }
        }
    }

    @Override
    @Transactional
    public Step save(Step step, Long recipeId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if(recipeOptional.isEmpty()){
            throw  new RuntimeException("Recipe was not founded with id: " + recipeId);
        }else{
            Recipe recipe = recipeOptional.get();
            recipe.addStep(step);
            recipeRepository.save(recipe);
            return step;
        }
    }
}
