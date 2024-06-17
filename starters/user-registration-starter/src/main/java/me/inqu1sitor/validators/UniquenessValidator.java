package me.inqu1sitor.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import me.inqu1sitor.interfaces.CheckUniqueness;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Annotation;

@RequiredArgsConstructor
public class UniquenessValidator<A extends Annotation,T> implements ConstraintValidator<A,T> {

    private final CheckUniqueness<T> checkUniqueness;

    @Override
    @Transactional
    public boolean isValid(T t, ConstraintValidatorContext context) {
        return checkUniqueness.check(t);
    }
}
