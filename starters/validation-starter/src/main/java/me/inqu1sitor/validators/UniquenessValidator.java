package me.inqu1sitor.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import me.inqu1sitor.interfaces.UniquenessChecker;

import java.lang.annotation.Annotation;

@RequiredArgsConstructor
public class UniquenessValidator<A extends Annotation, T> implements ConstraintValidator<A, T> {

    private final UniquenessChecker<T> uniquenessChecker;

    @Override
    public boolean isValid(T t, ConstraintValidatorContext context) {
        return uniquenessChecker.check(t);
    }
}
