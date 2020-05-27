package com.portfolio.recipebook.controller;

import com.portfolio.recipebook.command.IngredientCommand;
import com.portfolio.recipebook.dto.IngredientDto;
import com.portfolio.recipebook.service.IngredientService;
import com.portfolio.recipebook.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
public class IngredientController {

    private final IngredientService ingredientService;
    private final RecipeService recipeService;

    public IngredientController(IngredientService ingredientService, RecipeService recipeService) {
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listAndFrom(@PathVariable("recipeId") String recipeId,
                              Model model) {
        model.addAttribute("recipe", recipeService.findById(Long.valueOf(recipeId)));
        return "ingredient/listAndForm";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String delete(@PathVariable("recipeId") String recipeId,
                         @PathVariable("ingredientId") String ingredientId) {
        ingredientService.deleteOne(Long.valueOf(ingredientId), Long.valueOf(recipeId));
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

    @GetMapping("/api/recipes/{recipeId}/ingredients")
    public @ResponseBody List<IngredientDto>  getSet(@PathVariable("recipeId") Long recipeId) {
        return ingredientService.getAll(recipeId);
    }

    @PostMapping("/api/recipes/{recipeId}/ingredients/create")
    public @ResponseBody IngredientDto create(@PathVariable("recipeId") Long recipeId,
                                              @RequestBody IngredientCommand ingredientCommand) {
        return ingredientService.create(ingredientCommand, recipeId);
    }
}
