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
@ExistsUuid(role = Account.Role.ADMIN, status = Account.Status.ACTIVE, message = "Such admin not exists")
@Constraint(validatedBy = {})
public @interface ActiveAdminUuid {

    @AliasFor(annotation = ExistsUuid.class, attribute = "message")
    String message() default "";

    @AliasFor(annotation = ExistsUuid.class, attribute = "groups")
    Class<?>[] groups() default {};

    @AliasFor(annotation = ExistsUuid.class, attribute = "payload")
    Class<? extends Payload>[] payload() default {};
}
