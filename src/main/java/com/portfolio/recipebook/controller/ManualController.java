package com.portfolio.recipebook.controller;

import com.portfolio.recipebook.model.Manual;
import com.portfolio.recipebook.model.Recipe;
import com.portfolio.recipebook.model.Step;
import com.portfolio.recipebook.service.ImageService;
import com.portfolio.recipebook.service.ManualService;
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

    private final ManualService manualService;
    private final StepService stepService;
    private final ImageService imageService;

    public ManualController(ManualService manualService, StepService stepService, ImageService imageService) {
        this.manualService = manualService;
        this.stepService = stepService;
        this.imageService = imageService;
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
                             @RequestParam("imageStep") MultipartFile image,
                             @Valid @ModelAttribute("step") Step step,
                             BindingResult result,
                             Model model){

        try {
            boolean imageExist = image.getBytes().length != 0;

            if(result.hasErrors() || !imageExist){
                if(!imageExist){
                    result.addError(new FieldError("step","image","image must be chosen"));
                }
                result.getAllErrors().forEach(objectError -> {
                    log.debug(objectError.toString());
                });
                model.addAttribute("recipe", recipe);
                return "manual/indexAndFromStep";
            }else {
                stepService.saveAndSetToManual(step,recipe.getManual());
                imageService.saveImageFile(step, image);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "redirect:/recipe/" + recipe.getId() + "/manual";
    }

    @GetMapping("/recipe/{recipe}/manual/step/{step}/delete")
    public String delete(@PathVariable("recipe") Recipe recipe,
                         @PathVariable("step")Step step){

        recipe.getManual().getSteps().remove(step);
        stepService.delete(step);
        return "redirect:/recipe/" + recipe.getId() + "/manual";
    }

    @PostMapping("recipe/{recipe}/manual/step/{step}/edit")
    public String edit(@PathVariable("recipe") Recipe recipe,
                       @PathVariable("step") Step step,
                       @RequestParam("imageStep") MultipartFile image,
                       @RequestParam("descriptionStep") String description){

        try {
            if (step.getId() != null) {
                Byte[] currentImage = stepService.findById(step.getId()).getImage();
                if (image.getBytes().length != 0) {
                    imageService.saveImageFile(step, image);
                } else {
                    step.setImage(currentImage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        step.setDescription(description);



        return "redirect:/recipe/" + recipe.getId()  + "/manual";
    }
}
