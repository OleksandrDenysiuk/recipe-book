package com.portfolio.recipebook.service.impl;

import com.portfolio.recipebook.command.IngredientCommand;
import com.portfolio.recipebook.dto.IngredientDto;
import com.portfolio.recipebook.mapper.IngredientMapper;
import com.portfolio.recipebook.model.Ingredient;
import com.portfolio.recipebook.model.MeasureOfIngredient;
import com.portfolio.recipebook.model.Recipe;
import com.portfolio.recipebook.repository.IngredientRepository;
import com.portfolio.recipebook.repository.RecipeRepository;
import com.portfolio.recipebook.service.IngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<IngredientDto> getAll(Long recipeId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if(optionalRecipe.isPresent()){
            Recipe recipe = optionalRecipe.get();
            return recipe.getIngredients()
                    .stream()
                    .map(IngredientMapper::toDto)
                    .sorted(Comparator.comparing(IngredientDto::getId).reversed())
                    .collect(Collectors.toList());
        }else {
            throw new RuntimeException("Recipe not found");
        }

    }

    @Override
    public IngredientDto create(IngredientCommand ingredientCommand, Long recipeId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if(optionalRecipe.isPresent()){
            Recipe recipe = optionalRecipe.get();
            Ingredient ingredient = new Ingredient();
            ingredient.setRecipe(recipe);
            ingredient.setName(ingredientCommand.getName());
            ingredient.setAmount(ingredientCommand.getAmount());
            ingredient.setMeasure(MeasureOfIngredient.valueOf(ingredientCommand.getMeasure()));
            recipe.getIngredients().add(ingredient);
            return IngredientMapper.toDto(ingredientRepository.save(ingredient));
        }else{
            throw new RuntimeException("Recipe not found");
        }
    }

    @Override
    public Ingredient findOne(Long ingredientId, Long recipeId) {
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
    public void deleteOne(Long ingredientId, Long recipeId) {
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
