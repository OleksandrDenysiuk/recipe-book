package com.portfolio.recipebook.service;

import com.portfolio.recipebook.model.Ingredient;
import com.portfolio.recipebook.model.Recipe;

public interface IngredientService extends CrudService<Ingredient, Long> {
    Ingredient saveAndSetToRecipe(Ingredient ingredient, Recipe recipe);
}
