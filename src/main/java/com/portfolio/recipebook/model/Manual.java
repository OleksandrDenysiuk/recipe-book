package com.portfolio.recipebook.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "manuals")
public class Manual extends BaseEntity{

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "manual")
    Set<Step> steps = new HashSet<>();
}
