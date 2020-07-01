package com.portfolio.recipebook.controller.view;

import com.portfolio.recipebook.service.StepService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StepController {
    private final StepService stepService;

    public StepController(StepService stepService) {
        this.stepService = stepService;
    }

    @GetMapping("/recipes/{recipeId}/steps")
    public String getStepsPage(@PathVariable("recipeId") Long recipeId, Model model) {
        model.addAttribute("recipeId", recipeId);
        model.addAttribute("steps", stepService.getAllByRecipeId(recipeId));
        return "step/listForm";
    }
}
