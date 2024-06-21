package me.inqu1sitor.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import me.inqu1sitor.validators.EnumPartValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumPartValidator.class)
public @interface EnumPart {

    Class<? extends Enum<?>> enumClass();

    String message() default "String should be a part of required enum";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
