package me.inqu1sitor.authservice.annotations.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import me.inqu1sitor.authservice.entities.AccountEntity;
import me.inqu1sitor.authservice.validators.AccountExistsValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AccountExistsValidator.class)
public @interface AccountExists {

    AccountEntity.Role[] roles();

    AccountEntity.Status status();

    String message() default "Account deleted or not meet the requirements";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
