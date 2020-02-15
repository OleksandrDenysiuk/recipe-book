package com.portfolio.recipebook.service;

import com.portfolio.recipebook.model.Recipe;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void saveImageFile(Recipe recipe, MultipartFile file);
}
