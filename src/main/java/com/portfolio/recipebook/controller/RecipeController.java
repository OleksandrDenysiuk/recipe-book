package com.portfolio.recipebook.controller;

import com.portfolio.recipebook.model.Recipe;
import com.portfolio.recipebook.service.ImageService;
import com.portfolio.recipebook.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;
    private final ImageService imageService;

    public RecipeController(RecipeService recipeService, ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }


    @GetMapping("/all")
    public String viewRecipesList(Model model) {

    model.addAttribute("recipes", recipeService.findAll());
        return "recipe/list";
    }

    @GetMapping("/new")
    public String viewForm(Model model){
        model.addAttribute("recipe", new Recipe());
        return "recipe/form";
    }

    @PostMapping("/new")
    public String createRecipe(@Valid @ModelAttribute("recipe")Recipe recipe,
                               @RequestParam("imageRecipe") MultipartFile image,
                               BindingResult result){
        if(result.hasErrors()){
            result.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
        }

        imageService.saveImageFile(recipe,image);
        Recipe savedRecipe = recipeService.save(recipe);
        return "redirect:/recipe/" + savedRecipe.getId() + "/ingredients";
    }
}
