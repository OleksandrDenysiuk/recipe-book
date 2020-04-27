package com.portfolio.recipebook.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ingredients")
public class Ingredient{

    @Builder
    public Ingredient(Long id, String name, int amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 34)
    private String name;

    @Min(1)
    @Max(50)
    private int amount;

    @ManyToOne
    private Recipe recipe;

    @Enumerated(EnumType.STRING)
    private MeasureOfIngredient measure;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ingredient that = (Ingredient) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return 32;
    }
}
