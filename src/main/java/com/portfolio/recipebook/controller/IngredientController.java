package com.portfolio.recipebook.controller;

import com.portfolio.recipebook.model.Ingredient;
import com.portfolio.recipebook.model.Recipe;
import com.portfolio.recipebook.service.IngredientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/recipe/{id}/ingredients")
    public String listAndFrom(@PathVariable(value = "id") Recipe recipe,
                              Model model){
        model.addAttribute("recipe",recipe);
        model.addAttribute("ingredient", new Ingredient());
        return "ingredient/listAndForm";
    }

    @PostMapping("recipe/{recipe}/ingredients/new")
    public String createIngredient(@PathVariable Recipe recipe,
                                   @ModelAttribute(value = "ingredient") Ingredient ingredient,
                                   BindingResult result){
        ingredientService.saveAndSetToRecipe(ingredient,recipe);
        return "redirect:/recipe/" + recipe.getId() + "/ingredients";
    }
}
