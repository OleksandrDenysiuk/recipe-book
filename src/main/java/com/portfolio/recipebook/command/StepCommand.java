package com.portfolio.recipebook.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StepCommand {

    private Long id;

    private MultipartFile image;

    @Type(type = "text")
    @Size(max = 350)
    private String description;

}
