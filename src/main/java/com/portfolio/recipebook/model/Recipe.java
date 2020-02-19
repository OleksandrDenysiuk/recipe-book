package com.portfolio.recipebook.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "recipes")
public class Recipe extends BaseEntity{

    @NotBlank
    @Size(max = 34)
    private String title;

    @NotBlank
    @Lob
    @Type(type = "text")
    @Size(max = 350)
    private String description;

    @Min(1)
    @Max(360)
    private int cookTime;

    @OneToMany(cascade = CascadeType.ALL, mappedBy =  "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    @Lob
    private Byte[] image;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Manual manual;
}
