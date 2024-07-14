package me.inquis1tor.userservice.annotations.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import me.inquis1tor.userservice.dtos.AccountTransferDto;
import me.inquis1tor.userservice.dtos.CredentialsTransferDto;
import me.inquis1tor.userservice.validators.UniqueAccountDtoValidator;
import me.inquis1tor.userservice.validators.UniqueCredentialsDtoValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotated element must contain not registered email.
 * <p>
 * Supported types are:
 * <ul>
 *     <li>{@link CredentialsTransferDto}</li>
 *     <li>{@link AccountTransferDto}</li>
 * </ul>
 *
 * @author Alexander Sankevich
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UniqueCredentialsDtoValidator.class, UniqueAccountDtoValidator.class})
public @interface UniqueCredentials {

    String message() default "Email already registered";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
