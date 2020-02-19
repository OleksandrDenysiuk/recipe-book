package com.portfolio.recipebook.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "steps")
public class Step extends BaseEntity{

    @Lob
    private Byte[] image;

    @NotBlank
    @Lob
    @Type(type = "text")
    @Size(max = 350)
    private String description;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "manual_id", nullable = false)
    private Manual manual;
}
