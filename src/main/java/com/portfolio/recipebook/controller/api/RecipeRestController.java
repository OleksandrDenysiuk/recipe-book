package com.portfolio.recipebook.controller.api;

import com.portfolio.recipebook.dto.RecipeDto;
import com.portfolio.recipebook.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(RecipeRestController.BASE_URL)
public class RecipeRestController {

    public static final String BASE_URL = "/api";

    private final RecipeService recipeService;

    public RecipeRestController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/api/recipes")
    @ResponseStatus(HttpStatus.OK)
    public List<RecipeDto> getRecipeList(){
        return recipeService.getAll();
    }

    /*@PostMapping("/saveOrUpdate")
    public String createOrUpdate() {

                if (image.getBytes().length == 0) {
                    File imageEmptyFile = new File("src/main/resources/static/img/empty.png");
                    byte[] imageEmptyBytes = Files.readAllBytes(imageEmptyFile.toPath());
                    recipe.setImage(toObjects(imageEmptyBytes));
                }
    }


    private Byte[] toObjects(byte[] bytesPrim) {
        Byte[] bytes = new Byte[bytesPrim.length];
        Arrays.setAll(bytes, n -> bytesPrim[n]);
        return bytes;
    }*/
}
