package com.portfolio.recipebook.controller;

import com.portfolio.recipebook.dto.IngredientDto;
import com.portfolio.recipebook.model.Ingredient;
import com.portfolio.recipebook.service.IngredientService;
import com.portfolio.recipebook.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
                              Model model){
        model.addAttribute("recipe",recipeService.findById(Long.valueOf(recipeId)));
        model.addAttribute("ingredient", new Ingredient());
        return "ingredient/listAndForm";
    }

    @PostMapping("/recipe/{recipeId}/ingredients/new")
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
        }else{

            ingredientService.save(ingredient,Long.valueOf(recipeId));

            return "redirect:/recipe/" + recipeId + "/ingredients";
        }
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String delete(@PathVariable("recipeId") String recipeId,
                         @PathVariable("ingredientId")String ingredientId){
        ingredientService.deleteOne(Long.valueOf(ingredientId),Long.valueOf(recipeId));
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

    @GetMapping("/api/recipes/{recipeId}/ingredients")
    public @ResponseBody
    List<IngredientDto>
    getSet(@PathVariable("recipeId") Long recipeId){
        return ingredientService.getAll(recipeId);
    }
}
