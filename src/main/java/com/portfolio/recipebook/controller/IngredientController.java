package com.portfolio.recipebook.controller;

import com.portfolio.recipebook.command.IngredientCommand;
import com.portfolio.recipebook.dto.IngredientDto;
import com.portfolio.recipebook.service.IngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(IngredientController.BASE_URL)
public class IngredientController {

    public static final String BASE_URL = "/api";

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/recipes/{recipeId}/ingredients")
    @ResponseStatus(HttpStatus.OK)
    public List<IngredientDto> getSet(@PathVariable("recipeId") Long recipeId) {
        return ingredientService.getAll(recipeId);
    }

    @PostMapping("/recipes/{recipeId}/ingredients/create")
    @ResponseStatus(HttpStatus.CREATED)
    public IngredientDto create(@PathVariable("recipeId") Long recipeId,
                                              @RequestBody IngredientCommand ingredientCommand) {
        return ingredientService.create(ingredientCommand, recipeId);
    }

    @DeleteMapping("/recipes/{recipeId}/ingredients/{ingredientId}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("recipeId") String recipeId,
                       @PathVariable("ingredientId") String ingredientId) {
        ingredientService.delete(Long.valueOf(ingredientId), Long.valueOf(recipeId));
    }
}
