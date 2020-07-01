package com.portfolio.recipebook.controller.api;

import com.portfolio.recipebook.command.RecipeCommand;
import com.portfolio.recipebook.dto.RecipeDto;
import com.portfolio.recipebook.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(RecipeRestController.BASE_URL)
public class RecipeRestController {

    public static final String BASE_URL = "/api";

    private final RecipeService recipeService;

    public RecipeRestController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipes")
    @ResponseStatus(HttpStatus.OK)
    public List<RecipeDto> getAll() {
        return recipeService.getAll();
    }

    @GetMapping("/recipes/{recipeId}")
    @ResponseStatus(HttpStatus.OK)
    public RecipeDto getOneById(@PathVariable("recipeId") Long recipeId) {
        return recipeService.getOneById(recipeId);
    }

    @PostMapping("/recipes")
    @ResponseStatus(HttpStatus.CREATED)
    public RecipeDto create(@Valid RecipeCommand recipeCommand) throws IOException {
        return recipeService.create(recipeCommand);
    }

    @PutMapping("/recipes/{recipeId}")
    @ResponseStatus(HttpStatus.OK)
    public RecipeDto update(@PathVariable("recipeId") Long recipeId,
                            @Valid RecipeCommand recipeCommand){
        recipeCommand.setId(recipeId);
        return recipeService.update(recipeCommand);
    }

    @DeleteMapping("/recipes/{recipeId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("recipeId") Long recipeId) {
        recipeService.delete(recipeId);
    }
}
