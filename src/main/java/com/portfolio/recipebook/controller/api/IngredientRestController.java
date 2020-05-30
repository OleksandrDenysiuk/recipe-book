package com.portfolio.recipebook.controller.api;

import com.portfolio.recipebook.command.IngredientCommand;
import com.portfolio.recipebook.dto.IngredientDto;
import com.portfolio.recipebook.service.IngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(IngredientRestController.BASE_URL)
public class IngredientRestController {

    public static final String BASE_URL = "/api";

    private final IngredientService ingredientService;

    public IngredientRestController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/recipes/{recipeId}/ingredients")
    @ResponseStatus(HttpStatus.OK)
    public List<IngredientDto> getIngredientList(@PathVariable("recipeId") Long recipeId) {
        return ingredientService.getAll(recipeId);
    }

    @PostMapping("/recipes/{recipeId}/ingredients")
    @ResponseStatus(HttpStatus.CREATED)
    public IngredientDto create(@PathVariable("recipeId") Long recipeId,
                                              @Valid @RequestBody IngredientCommand ingredientCommand) {
        return ingredientService.create(ingredientCommand, recipeId);
    }

    @DeleteMapping("/recipes/{recipeId}/ingredients/{ingredientId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("recipeId") String recipeId,
                       @PathVariable("ingredientId") String ingredientId) {
        ingredientService.delete(Long.valueOf(ingredientId), Long.valueOf(recipeId));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody Map<String, String> validationError(MethodArgumentNotValidException ex) {

        Map<String, String> errorsMap = new HashMap<>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            FieldError fieldError = (FieldError) error;
            errorsMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return errorsMap;
    }
}
