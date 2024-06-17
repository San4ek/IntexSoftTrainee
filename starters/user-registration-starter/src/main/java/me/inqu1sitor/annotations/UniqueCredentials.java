package me.inqu1sitor.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import me.inqu1sitor.validators.CredentialsUniquenessValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CredentialsUniquenessValidator.class)
public @interface UniqueCredentials {

    String message() default "Credentials should be unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
