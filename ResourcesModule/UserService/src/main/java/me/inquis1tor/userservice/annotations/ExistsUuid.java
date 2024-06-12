package me.inquis1tor.userservice.annotations;

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
public @interface ExistsUuid {
    Account.Status status();
    Account.Role[] role();
    String parameter() default "";
    String message() default "Account not exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
