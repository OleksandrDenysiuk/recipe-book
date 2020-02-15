package com.portfolio.recipebook.repository;

import com.portfolio.recipebook.model.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
