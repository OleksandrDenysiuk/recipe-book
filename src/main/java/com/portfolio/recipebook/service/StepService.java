package com.portfolio.recipebook.service;

import com.portfolio.recipebook.model.Step;

public interface StepService{
    Step save(Step step, Long recipeId);
    Step findOne(Long stepId, Long recipeId);
    void deleteOne(Long stepId, Long recipeId);
}
