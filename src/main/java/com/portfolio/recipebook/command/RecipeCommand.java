package com.portfolio.recipebook.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeCommand {

    private Long id;

    @NotBlank
    @Size(max = 34)
    private String title;

    @NotBlank
    @Type(type = "text")
    @Size(max = 350)
    private String description;

    @Min(1)
    @Max(360)
    private int cookTime;

    private MultipartFile image;

    private String difficulty;
}
