package me.inqu1sitor.authservice.annotations.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import me.inqu1sitor.authservice.dtos.CredentialsRequestDto;
import me.inqu1sitor.authservice.validators.UniqueCredentialsValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotated element must contain not registered email.
 * <p>
 * Supported types are:
 * <ul>
 *     <li>{@link CredentialsRequestDto}</li>
 * </ul>
 *
 * @author Alexander Sankevich
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueCredentialsValidator.class)
public @interface UniqueCredentials {

    String message() default "Email already registered";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
