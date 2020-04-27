package com.portfolio.recipebook.controller;

import com.portfolio.recipebook.model.Recipe;
import com.portfolio.recipebook.model.Step;
import com.portfolio.recipebook.service.RecipeService;
import com.portfolio.recipebook.service.StepService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {

    private final StepService stepService;
    private final RecipeService recipeService;

    public ImageController(StepService stepService, RecipeService recipeService) {
        this.stepService = stepService;
        this.recipeService = recipeService;
    }

    @GetMapping("recipe/{recipeId}/image")
    public void renderImageRecipeFromDB(@PathVariable("recipeId") String recipeId, HttpServletResponse response) throws IOException {
        Recipe recipe = recipeService.findById(Long.valueOf(recipeId));
        renderImage(response, recipe.getImage());
    }


    @GetMapping("/recipe/{recipeId}/step/{stepId}/image")
    public void renderImageStepFromDB(@PathVariable("recipeId") String recipeId,
                                      @PathVariable("stepId") String stepId,
                                      HttpServletResponse response) throws IOException {
        Step step = stepService.findOne(Long.valueOf(stepId),Long.valueOf(recipeId));
        renderImage(response, step.getImage());
    }

    private void renderImage(HttpServletResponse response, Byte[] image) throws IOException {
        if (image != null) {
            byte[] byteArray = new byte[image.length];
            int i = 0;

            for (Byte wrappedByte : image){
                byteArray[i++] = wrappedByte; //auto unboxing
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }

}
