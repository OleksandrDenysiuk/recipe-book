package com.portfolio.recipebook.controller.api;

import com.portfolio.recipebook.command.StepCommand;
import com.portfolio.recipebook.dto.StepDto;
import com.portfolio.recipebook.service.StepService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class StepRestController {

    private final StepService stepService;

    public StepRestController(StepService stepService) {
        this.stepService = stepService;
    }

    @GetMapping("/api/recipes/{recipeId}/steps")
    @ResponseStatus(HttpStatus.OK)
    public List<StepDto> getStepList(@PathVariable("recipeId") Long recipeId) {
        return stepService.getAll(recipeId);
    }

    @GetMapping("/api/recipes/{recipeId}/steps/{stepId}")
    @ResponseStatus(HttpStatus.OK)
    public StepDto getStepList(@PathVariable("recipeId") Long recipeId,
                                     @PathVariable("stepId") Long stepId) {
        return stepService.getByIdAndRecipeId(stepId, recipeId);
    }

    @PostMapping("/api/recipes/{recipeId}/steps")
    @ResponseStatus(HttpStatus.CREATED)
    public StepDto create(@PathVariable("recipeId") Long recipeId,
                          @Valid StepCommand stepCommand){
        return stepService.create(stepCommand,recipeId);
    }

    @DeleteMapping("/api/recipes/{recipeId}/steps/{stepId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("recipeId") Long recipeId,
                         @PathVariable("stepId") Long stepId) {
        stepService.delete(stepId, recipeId);
    }

    @PutMapping("/api/recipes/{recipeId}/steps/{stepId}")
    @ResponseStatus(HttpStatus.OK)
    public StepDto edit(@PathVariable("recipeId") Long recipeId,
                       @PathVariable("stepId") Long stepId,
                       StepCommand stepCommand) {
        stepCommand.setId(stepId);
        return stepService.update(stepCommand, recipeId);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody Map<String, String> handleException(BindException e,
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
