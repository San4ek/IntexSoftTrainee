package me.inqu1sitor.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import me.inqu1sitor.holders.EnumValuesHolder;
import me.inqu1sitor.annotations.EnumPart;

public class EnumPartValidator implements ConstraintValidator<EnumPart, String> {

    private Class<? extends Enum> enumClass;
    private EnumValuesHolder<? extends Enum<?>> enumValuesHolder;

    @Override
    public void initialize(EnumPart constraintAnnotation) {
        enumClass = constraintAnnotation.enumClass();
        enumValuesHolder=new EnumValuesHolder<>(enumClass);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return enumValuesHolder.contains(value);
    }
}
