package com.portfolio.recipebook.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StepDto {

    private Long id;

    private Byte[] image;

    private String description;

    private Long recipeId;
}
