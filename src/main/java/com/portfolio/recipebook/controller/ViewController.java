package com.portfolio.recipebook.controller;

import com.portfolio.recipebook.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ViewController {
    private final RecipeService recipeService;

    public ViewController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping({"","/","index.html"})
    public String getIndexPage(){
        return "index";
    }

    @GetMapping("/recipes/{recipeId}/ingredients/page")
    public String getIngredientsPage(@PathVariable("recipeId") String recipeId,
                              Model model) {
        model.addAttribute("recipe", recipeService.findById(Long.valueOf(recipeId)));
        return "ingredient/listAndForm";
    }
}
