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
import java.io.IOException;

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
    public String viewList(Model model) {

        model.addAttribute("recipes", recipeService.findAll());
        return "recipe/list";
    }

    @GetMapping("/{id}/details")
    public String viewDetails(@PathVariable("id") Recipe recipe, Model model) {
        model.addAttribute("recipe", recipe);
        return "recipe/index";
    }

    @GetMapping("/new")
    public String viewForm(Model model) {
        model.addAttribute("recipe", new Recipe());
        return "recipe/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Recipe recipe, Model model) {
        model.addAttribute("recipe", recipe);
        return "recipe/form";
    }

    @PostMapping("/saveOrUpdate")
    public String createOrUpdate(@Valid @ModelAttribute("recipe") Recipe recipe,
                                 @RequestParam("imageRecipe") MultipartFile image,
                                 BindingResult result) {

        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
        }

        try {
            if (recipe.getId() != null) {
                Byte[] currentImage = recipeService.findById(recipe.getId()).getImage();
                if (image.getBytes().length != 0) {
                    imageService.saveImageFile(recipe, image);
                } else {
                    recipe.setImage(currentImage);
                }
            } else {
                imageService.saveImageFile(recipe, image);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Recipe savedRecipe = recipeService.save(recipe);
        return "redirect:/recipe/" + savedRecipe.getId() + "/ingredients";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id")Recipe recipe){

        recipeService.delete(recipe);

        return "redirect:/recipe/all";
    }
}
