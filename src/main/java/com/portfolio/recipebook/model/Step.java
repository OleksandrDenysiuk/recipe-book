package com.portfolio.recipebook.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "steps")
public class Step extends BaseEntity{

    @Builder
    public Step(Long id, Byte[] image, String description){
        super(id);
        this.image = image;
        this.description = description;
    }

    @Lob
    private Byte[] image;

    @Lob
    @Type(type = "text")
    @Size(max = 350)
    private String description;

    @ManyToOne
    private Recipe recipe;
}
