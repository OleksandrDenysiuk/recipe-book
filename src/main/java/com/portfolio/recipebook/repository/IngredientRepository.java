package com.portfolio.recipebook.repository;

import com.portfolio.recipebook.model.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}
