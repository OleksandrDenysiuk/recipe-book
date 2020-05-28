package com.portfolio.recipebook.dto;

import com.portfolio.recipebook.model.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDto {
    private Long id;

    private String title;

    private String description;

    private int cookTime;

    @Lob
    private Byte[] image;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    private List<IngredientDto> ingredients = new ArrayList<>();

    private List<StepDto> steps = new ArrayList<>();
}
