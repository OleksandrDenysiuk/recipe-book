package com.portfolio.recipebook.controller.view;

import com.portfolio.recipebook.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IngredientController {

    private final RecipeService recipeService;

    public IngredientController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipes/{recipeId}/ingredients")
    public String getIngredientsPage(@PathVariable("recipeId") Long recipeId,
                                     Model model) {
        model.addAttribute("recipe", recipeService.getById(recipeId));
        return "ingredient/listForm";
    }
}
