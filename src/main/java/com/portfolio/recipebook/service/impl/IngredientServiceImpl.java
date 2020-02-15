package com.portfolio.recipebook.service.impl;

import com.portfolio.recipebook.model.Ingredient;
import com.portfolio.recipebook.model.Recipe;
import com.portfolio.recipebook.repository.IngredientRepository;
import com.portfolio.recipebook.repository.RecipeRepository;
import com.portfolio.recipebook.service.IngredientService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, RecipeRepository recipeRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Ingredient> findAll() {
        Set<Ingredient> ingredients = new HashSet<>();
        ingredientRepository.findAll().forEach(ingredients::add);
        return ingredients;
    }

    @Override
    public Ingredient findById(Long aLong) {
        return ingredientRepository.findById(aLong).orElse(null);
    }

    @Override
    public Ingredient save(Ingredient object) {
        return ingredientRepository.save(object);
    }

    @Override
    public Ingredient saveAndSetToRecipe(Ingredient ingredient, Recipe recipe) {
        ingredient.setRecipe(recipe);
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        recipe.getIngredients().add(ingredient);
        return savedIngredient;
    }

    @Override
    public void delete(Ingredient object) {
        ingredientRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        ingredientRepository.deleteById(aLong);
    }
}
