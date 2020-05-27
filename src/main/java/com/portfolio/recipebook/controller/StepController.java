package com.portfolio.recipebook.controller;

import com.portfolio.recipebook.command.StepCommand;
import com.portfolio.recipebook.dto.StepDto;
import com.portfolio.recipebook.service.StepService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class StepController {

    private final StepService stepService;

    public StepController(StepService stepService) {
        this.stepService = stepService;
    }

    @GetMapping("/api/recipes/{recipeId}/steps")
    @ResponseStatus(HttpStatus.OK)
    public List<StepDto> getStepList(@PathVariable("recipeId") Long recipeId) {
        return stepService.getAll(recipeId);
    }

    @PostMapping("/api/recipes/{recipeId}/steps/create")
    @ResponseStatus(HttpStatus.OK)
    public StepDto create(@PathVariable("recipeId") Long recipeId,
                          @ModelAttribute StepCommand stepCommand) {
        return stepService.create(stepCommand,recipeId);
    }

    @DeleteMapping("/api/recipes/{recipeId}/steps/{stepId}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("recipeId") Long recipeId,
                         @PathVariable("stepId") Long stepId) {
        stepService.delete(stepId, recipeId);
    }

    @PutMapping("recipe/{recipeId}/manual/step/{stepId}/edit")
    @ResponseStatus(HttpStatus.OK)
    public StepDto edit(@PathVariable("recipeId") Long recipeId,
                       @PathVariable("stepId") Long stepId,
                       @ModelAttribute StepCommand stepCommand) {
        stepCommand.setId(stepId);
        return stepService.update(stepCommand, recipeId);
    }
}