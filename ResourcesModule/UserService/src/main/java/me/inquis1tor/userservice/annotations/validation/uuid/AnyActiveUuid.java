package me.inquis1tor.userservice.annotations.validation.uuid;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import me.inquis1tor.userservice.entities.Account;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@ExistsUuidWithParams(roles = {Account.Role.USER, Account.Role.MODER, Account.Role.ADMIN},
        status = Account.Status.ACTIVE,
        message = "Account not exists or blocked")
@Constraint(validatedBy = {})
public @interface AnyActiveUuid {

    @AliasFor(annotation = ExistsUuidWithParams.class)
    String message() default "";

    @AliasFor(annotation = ExistsUuidWithParams.class)
    Class<?>[] groups() default {};

    @AliasFor(annotation = ExistsUuidWithParams.class)
    Class<? extends Payload>[] payload() default {};
}
