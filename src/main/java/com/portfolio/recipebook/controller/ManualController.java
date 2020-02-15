package com.portfolio.recipebook.controller;

import com.portfolio.recipebook.model.Manual;
import com.portfolio.recipebook.model.Recipe;
import com.portfolio.recipebook.model.Step;
import com.portfolio.recipebook.service.ManualService;
import com.portfolio.recipebook.service.StepService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ManualController {

    private final ManualService manualService;
    private final StepService stepService;

    public ManualController(ManualService manualService, StepService stepService) {
        this.manualService = manualService;
        this.stepService = stepService;
    }

    @GetMapping("recipe/{id}/manual")
    public String viewManual(@PathVariable(value = "id") Recipe recipe, Model model){
        if(recipe.getManual() == null){
            Manual manual = new Manual();
            manualService.saveAndSetToRecipe(manual,recipe);
        }
        model.addAttribute("recipe", recipe);
        model.addAttribute("step", new Step());
        return "manual/indexAndFromStep";
    }

    @PostMapping("recipe/{recipe}/manual/step/new")
    public String createStep(@PathVariable Recipe recipe,
                             @Valid @ModelAttribute Step step,
                             BindingResult result){
        stepService.saveAndSetToManual(step,recipe.getManual());
        return "redirect:/recipe/" + recipe.getId() + "/manual";
    }
}
