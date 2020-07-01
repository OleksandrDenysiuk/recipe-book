package com.portfolio.recipebook.controller.view;

import com.portfolio.recipebook.service.IngredientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }


    @GetMapping("/recipes/{recipeId}/ingredients")
    public String getIngredientsPage(@PathVariable("recipeId") Long recipeId,
                                     Model model) {
        model.addAttribute("recipeId", recipeId);
        model.addAttribute("ingredients", ingredientService.getAllByRecipeId(recipeId));
        return "ingredient/listForm";
    }
}
