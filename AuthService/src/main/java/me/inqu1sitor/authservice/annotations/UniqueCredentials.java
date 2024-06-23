package me.inqu1sitor.authservice.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import me.inqu1sitor.authservice.validators.UniqueCredentialsValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueCredentialsValidator.class)
public @interface UniqueCredentials {

    String message() default "Credentials should be unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}