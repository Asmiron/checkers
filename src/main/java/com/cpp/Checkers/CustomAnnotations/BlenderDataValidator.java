package com.cpp.Checkers.CustomAnnotations;

import com.cpp.Checkers.CustomAnnotations.FieldsMinMax;
import com.cpp.Checkers.Models.BlenderData;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.reflect.Field;

public class BlenderDataValidator implements ConstraintValidator<FieldsMinMax, Object> {

    String min;
    String max;

    @Override
    public void initialize(final FieldsMinMax constraintAnnotation) {
        min = constraintAnnotation.Min();
        max = constraintAnnotation.Max();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            final Field minValueField = value.getClass().getDeclaredField(min);
            minValueField.setAccessible(true);

            final Field maxValueField = value.getClass().getDeclaredField(max);
            maxValueField.setAccessible(true);

            final Float minValue = (Float) minValueField.get(value);
            final Float maxValue = (Float) maxValueField.get(value);


            if (minValue != null && maxValue != null) {
                int compare = maxValue.compareTo(minValue);
                return compare > 0;
            }
            return false;
        } catch (final Exception e) {
            e.printStackTrace();

            return false;
        }
    }
}

