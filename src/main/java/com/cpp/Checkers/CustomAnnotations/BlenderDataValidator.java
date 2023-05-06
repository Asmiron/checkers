package com.cpp.Checkers.CustomAnnotations;

import com.cpp.Checkers.CustomAnnotations.FieldsMinMax;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class BlenderDataValidator implements ConstraintValidator<FieldsMinMax, Integer> {

    private String min;
    private String FieldToMatch;

    @Override
    public void initialize(FieldsMinMax constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.FieldToMatch = constraintAnnotation.FieldToMatch();
    }

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        Integer fieldMin = (Integer) new BeanWrapperImpl(integer).getPropertyValue(min);
        Integer fieldMatch = (Integer) new BeanWrapperImpl(integer).getPropertyValue(FieldToMatch);
        if (fieldMin != null && fieldMatch != null) return fieldMatch>=fieldMin;
        return false;
    }

}
