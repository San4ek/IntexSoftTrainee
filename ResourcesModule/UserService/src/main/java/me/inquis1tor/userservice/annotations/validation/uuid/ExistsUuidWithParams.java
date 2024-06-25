package me.inquis1tor.userservice.annotations.validation.uuid;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import me.inquis1tor.userservice.entities.Account;
import me.inquis1tor.userservice.validators.ExistsUuidValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistsUuidValidator.class)
public @interface ExistsUuidWithParams {

    Account.Status status();

    Account.Role[] roles();

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
