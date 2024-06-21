package me.inqu1sitor.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import me.inqu1sitor.annotations.EnumPart;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class EnumPartValidator implements ConstraintValidator<EnumPart, String> {

    private Set<String> enumSet;

    @Override
    public void initialize(EnumPart constraintAnnotation) {
        enumSet = Arrays.stream(constraintAnnotation.enumClass().
                getEnumConstants()).map(Enum::name).collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        System.out.println(value);
        return enumSet.contains(value);
    }
}
