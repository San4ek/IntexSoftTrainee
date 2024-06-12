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
@ExistsUuid(role = {Account.Role.USER, Account.Role.MODER}, status = Account.Status.BLOCKED)
public @interface BlockedAccountUuid {
    @AliasFor(annotation = ExistsUuid.class)
    String parameter() default "accountId";
    @AliasFor(annotation = ExistsUuid.class)
    String message() default "Account not exists or not blocked";
    @AliasFor(annotation = ExistsUuid.class)
    Class<?>[] groups() default {};
    @AliasFor(annotation = ExistsUuid.class)
    Class<? extends Payload>[] payload() default {};
}
