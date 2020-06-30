package com.portfolio.recipebook.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = ImageValidator.class)
@Documented
public @interface Image {
    String message() default "must not be empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
