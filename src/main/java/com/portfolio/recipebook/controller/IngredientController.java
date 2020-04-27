package com.portfolio.recipebook.controller;

import com.portfolio.recipebook.model.Ingredient;
import com.portfolio.recipebook.model.Recipe;
import com.portfolio.recipebook.service.IngredientService;
import com.portfolio.recipebook.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
public class IngredientController {

    private final IngredientService ingredientService;
    private final RecipeService recipeService;

    public IngredientController(IngredientService ingredientService, RecipeService recipeService) {
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/ingredients")
    public String listAndFrom(@PathVariable(value = "id") Recipe recipe,
                              Model model){
        model.addAttribute("recipe",recipe);
        model.addAttribute("ingredient", new Ingredient());
        return "ingredient/listAndForm";
    }

    @PostMapping("recipe/{recipeId}/ingredients/new")
    public String createIngredient(@PathVariable String recipeId,
                                   @Valid @ModelAttribute(value = "ingredient") Ingredient ingredient,
                                   BindingResult result,
                                   Model model){

        if(result.hasErrors()){
            result.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            model.addAttribute("recipe",recipeService.findById(Long.valueOf(recipeId)));

            return "ingredient/listAndForm";
        }
        ingredientService.save(ingredient,Long.valueOf(recipeId));
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String delete(@PathVariable("recipeId") String recipeId,
                         @PathVariable("ingredientId")String ingredientId){
        ingredientService.deleteById(Long.valueOf(ingredientId),Long.valueOf(recipeId));
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }
}
