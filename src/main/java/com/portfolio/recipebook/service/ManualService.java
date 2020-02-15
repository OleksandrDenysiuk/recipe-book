package com.portfolio.recipebook.service;

import com.portfolio.recipebook.model.Manual;
import com.portfolio.recipebook.model.Recipe;

public interface ManualService extends CrudService<Manual, Long> {
    Manual saveAndSetToRecipe(Manual manual, Recipe recipe);
}
