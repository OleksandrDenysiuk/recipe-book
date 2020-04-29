package com.portfolio.recipebook.controller;

import com.portfolio.recipebook.model.Step;
import com.portfolio.recipebook.service.ImageService;
import com.portfolio.recipebook.service.RecipeService;
import com.portfolio.recipebook.service.StepService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@Controller
public class ManualController {

    private final StepService stepService;
    private final ImageService imageService;
    private final RecipeService recipeService;

    public ManualController(StepService stepService, ImageService imageService, RecipeService recipeService) {
        this.stepService = stepService;
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("recipe/{recipeId}/manual")
    public String viewManual(@PathVariable("recipeId") String recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findById(Long.valueOf(recipeId)));
        model.addAttribute("step", new Step());
        return "manual/indexAndFromStep";
    }

    @PostMapping("recipe/{recipeId}/manual/step/new")
    public String createStep(@PathVariable("recipeId") String recipeId,
                             @RequestParam("imageStep") MultipartFile image,
                             @Valid @ModelAttribute("step") Step step,
                             BindingResult result,
                             Model model) {

        try {
            boolean imageExist = image.getBytes().length != 0;

            if (result.hasErrors() || !imageExist) {
                if (!imageExist) {
                    result.addError(new FieldError("step", "image", "image must be chosen"));
                }
                result.getAllErrors().forEach(objectError -> {
                    log.debug(objectError.toString());
                });
                model.addAttribute("recipe", recipeService.findById(Long.valueOf(recipeId)));
                return "manual/indexAndFromStep";
            } else {
                stepService.save(imageService.setImageFile(step, image), Long.valueOf(recipeId));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/recipe/" + recipeId + "/manual";
    }

    @GetMapping("/recipe/{recipeId}/manual/step/{stepId}/delete")
    public String delete(@PathVariable("recipeId") String recipeId,
                         @PathVariable("stepId") String stepId) {

        stepService.deleteOne(Long.valueOf(stepId), Long.valueOf(recipeId));

        return "redirect:/recipe/" + recipeId + "/manual";
    }

    @PostMapping("recipe/{recipeId}/manual/step/{stepId}/edit")
    public String edit(@PathVariable("recipeId") String recipeId,
                       @PathVariable("stepId") String stepId,
                       @RequestParam("imageStep") MultipartFile image,
                       @RequestParam("descriptionStep") String description) {
        Step currentStep = stepService.findOne(Long.valueOf(stepId), Long.valueOf(recipeId));

        try {
            Byte[] currentImage = currentStep.getImage();
            if (image.getBytes().length != 0) {
                imageService.setImageFile(currentStep, image);
            } else {
                currentStep.setImage(currentImage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentStep.setDescription(description);
        return "redirect:/recipe/" + recipeId + "/manual";
    }
}
