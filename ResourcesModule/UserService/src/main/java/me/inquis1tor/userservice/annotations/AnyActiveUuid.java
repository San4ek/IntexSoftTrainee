package me.inquis1tor.userservice.annotations;

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
@ExistsUuid(roles = {Account.Role.USER, Account.Role.MODER, Account.Role.ADMIN},
        status = Account.Status.ACTIVE,
        message = "Account not exists or blocked")
@Constraint(validatedBy = {})
public @interface AnyActiveUuid {
    @AliasFor(annotation = ExistsUuid.class)
    String message() default "";

    @AliasFor(annotation = ExistsUuid.class)
    Class<?>[] groups() default {};

    @AliasFor(annotation = ExistsUuid.class)
    Class<? extends Payload>[] payload() default {};
}
