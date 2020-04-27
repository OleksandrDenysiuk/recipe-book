package com.portfolio.recipebook.service;

import com.portfolio.recipebook.model.Recipe;

import java.util.Set;

public interface RecipeService{
    Set<Recipe> findAll();
    Recipe findById(Long id);
    Recipe save(Recipe object);
    void deleteById(Long id);
}
