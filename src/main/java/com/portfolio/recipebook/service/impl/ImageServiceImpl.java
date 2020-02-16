package com.portfolio.recipebook.service.impl;

import com.portfolio.recipebook.model.Recipe;
import com.portfolio.recipebook.model.Step;
import com.portfolio.recipebook.repository.RecipeRepository;
import com.portfolio.recipebook.repository.StepRepository;
import com.portfolio.recipebook.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {


    private final RecipeRepository recipeRepository;
    private final StepRepository stepRepository;

    public ImageServiceImpl(RecipeRepository recipeService, StepRepository stepRepository) {
        this.recipeRepository = recipeService;
        this.stepRepository = stepRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(Recipe recipe, MultipartFile file) {

        try {
            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()){
                byteObjects[i++] = b;
            }

            recipe.setImage(byteObjects);

            recipeRepository.save(recipe);
        } catch (IOException e) {
            log.error("Error occurred", e);
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void saveImageFile(Step step, MultipartFile file) {

        try {
            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()){
                byteObjects[i++] = b;
            }

            step.setImage(byteObjects);

            stepRepository.save(step);
        } catch (IOException e) {
            log.error("Error occurred", e);
            e.printStackTrace();
        }
    }
}

