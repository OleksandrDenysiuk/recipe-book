package com.portfolio.recipebook.service.impl;

import com.portfolio.recipebook.model.Ingredient;
import com.portfolio.recipebook.model.Recipe;
import com.portfolio.recipebook.repository.RecipeRepository;
import com.portfolio.recipebook.service.IngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;

    public IngredientServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Ingredient findByIngredientIdAndRecipeId(Long ingredientId, Long recipeId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if(recipeOptional.isEmpty()){
            throw new RuntimeException("Recipe was not founded by id: " + recipeId);
        }else{
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();

            if (ingredientOptional.isEmpty()) {
                log.error("Ingredient id not found: " + ingredientId);
                throw new RuntimeException("Ingredient id not found: " + ingredientId);
            }else {
                return ingredientOptional.get();
            }
        }
    }

    @Override
    public Ingredient save(Ingredient ingredient, Long recipeId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if(recipeOptional.isEmpty()){
            throw new RuntimeException("Recipe was not founded by id: " + recipeId);
        }else{
            Recipe recipe = recipeOptional.get();
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
            recipeRepository.save(recipe);
        }
        return ingredient;
    }

    @Override
    public void deleteById(Long ingredientId, Long recipeId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (recipeOptional.isEmpty()){
            throw new RuntimeException("Recipe was not founded by id: " + recipeId);
        }else{
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();

            if (ingredientOptional.isEmpty()){
                throw new RuntimeException("Ingredient was not founded by id: " + ingredientId);
            }else {
                ingredientOptional.get().setRecipe(null);
                recipe.getIngredients().remove(ingredientOptional.get());
                recipeRepository.save(recipe);
            }
        }
    }
}
