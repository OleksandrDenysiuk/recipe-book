package com.portfolio.recipebook.service;

import com.portfolio.recipebook.model.Recipe;
import com.portfolio.recipebook.model.Step;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void saveImageFile(Recipe recipe, MultipartFile file);

    Step setImageFile(Step step, MultipartFile file);
}
