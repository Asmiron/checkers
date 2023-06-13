package com.cpp.Checkers.CustomAnnotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = BlenderDataValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldsMinMax {
    String message() default "Fields values don't match!";

    String Min();

    String Max();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };


    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        FieldsMinMax[] value();
    }
}
