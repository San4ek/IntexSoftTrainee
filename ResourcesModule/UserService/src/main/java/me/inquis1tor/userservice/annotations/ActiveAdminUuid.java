package me.inquis1tor.userservice.annotations;

import jakarta.validation.Payload;
import me.inquis1tor.userservice.entities.Account;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@ExistsUuid(role = Account.Role.ADMIN, status = Account.Status.ACTIVE)
public @interface ActiveAdminUuid {
    @AliasFor(annotation = ExistsUuid.class)
    String parameter() default "Administrator";

    @AliasFor(annotation = ExistsUuid.class)
    String message() default "Such admin not exists";
    @AliasFor(annotation = ExistsUuid.class)
    Class<?>[] groups() default {};
    @AliasFor(annotation = ExistsUuid.class)
    Class<? extends Payload>[] payload() default {};
}
