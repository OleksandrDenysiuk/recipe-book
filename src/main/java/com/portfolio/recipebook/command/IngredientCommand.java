package com.portfolio.recipebook.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientCommand {

    @NotBlank
    @Size(max = 34)
    private String name;

    @Min(1)
    @Max(50)
    private int amount;

    private String measure;
}
