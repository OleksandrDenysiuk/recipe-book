package com.portfolio.recipebook.controller.view;

import com.portfolio.recipebook.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipes")
    public String recipeList(Model model) {
        model.addAttribute("recipes", recipeService.getAll());
        return "recipe/list";
    }

    @GetMapping("/recipes/{recipeId}")
    public String recipeIndex(@PathVariable("recipeId") Long recipeId,
                             Model model) {
        model.addAttribute("recipe", recipeService.getById(recipeId));
        return "recipe/index";
    }

    @GetMapping("/recipes/{recipeId}/form")
    public String recipeEditForm(@PathVariable("recipeId") Long recipeId,
                             Model model) {
        model.addAttribute("recipe", recipeService.getById(recipeId));
        return "recipe/form";
    }

    @GetMapping("/recipes/form")
    public String recipeCreateForm() {
        return "recipe/form";
    }
}
