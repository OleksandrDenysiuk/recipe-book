package com.portfolio.recipebook.service.impl;

import com.portfolio.recipebook.model.Recipe;
import com.portfolio.recipebook.repository.RecipeRepository;
import com.portfolio.recipebook.service.RecipeService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> findAll() {
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().forEach(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(Long id) {

        Optional<Recipe> recipe = recipeRepository.findById(id);

        if(recipe.isEmpty()){
            throw new RuntimeException("Recipe not found with id: " + id);
        }

        return recipe.get();
    }

    @Override
    public Recipe save(Recipe object) {
        return recipeRepository.save(object);
    }


    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }

}
