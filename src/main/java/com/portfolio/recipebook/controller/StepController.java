package com.portfolio.recipebook.controller;

import com.portfolio.recipebook.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StepController {
    private final RecipeService recipeService;

    public StepController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("recipes/{recipeId}/steps")
    public String getStepsPage(@PathVariable("recipeId") Long recipeId, Model model) {
        model.addAttribute("recipe", recipeService.getById(recipeId));
        return "manual/indexAndFromStep";
    }
}
