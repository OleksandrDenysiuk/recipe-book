package com.portfolio.recipebook.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "steps")
public class Step extends BaseEntity{

    @Lob
    private Byte[] image;

    private String description;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "manual_id", nullable = false)
    private Manual manual;
}
