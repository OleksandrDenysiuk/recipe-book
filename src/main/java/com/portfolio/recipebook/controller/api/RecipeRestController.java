package com.portfolio.recipebook.controller.api;

import com.portfolio.recipebook.command.RecipeCommand;
import com.portfolio.recipebook.dto.RecipeDto;
import com.portfolio.recipebook.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<RecipeDto> getRecipeList(){
        return recipeService.getAll();
    }

    @GetMapping("/recipes/{recipeId}")
    @ResponseStatus(HttpStatus.OK)
    public RecipeDto getRecipe(@PathVariable("recipeId") Long recipeId){
        return recipeService.getById(recipeId);
    }

    @PostMapping("/recipes")
    @ResponseStatus(HttpStatus.CREATED)
    public RecipeDto create(@Valid @ModelAttribute RecipeCommand recipeCommand) throws IOException {
        return recipeService.create(recipeCommand);
    }

    @PutMapping("/recipes/{recipeId}")
    @ResponseStatus(HttpStatus.OK)
    public RecipeDto update(@PathVariable("recipeId") Long recipeId,
                             @Valid @ModelAttribute RecipeCommand recipeCommand,
                            BindingResult result) throws BindException {
        if(result.hasErrors()){
            throw new BindException(result);
        }
        recipeCommand.setId(recipeId);
        return recipeService.update(recipeCommand);
    }

    @DeleteMapping("/recipes/{recipeId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("recipeId") Long recipeId){
        recipeService.delete(recipeId);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    Map<String, String> handleException(BindException e,
                                        HttpServletRequest request,
                                        HttpServletResponse response)
    {
        Map<String, String> errorsMap = new HashMap<>();

        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            FieldError fieldError = (FieldError) error;
            errorsMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return errorsMap;
    }
}
