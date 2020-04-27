package com.portfolio.recipebook.service;

import com.portfolio.recipebook.model.Manual;
import com.portfolio.recipebook.model.Recipe;

import java.util.Set;

public interface ManualService{
    Manual saveAndSetToRecipe(Manual manual, Recipe recipe);
    Set<Manual> findAll();
    Manual findById(Long aLong);
    void delete(Manual object);
    void deleteById(Long aLong);
}
