package me.inquis1tor.userservice.annotations.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import me.inquis1tor.userservice.entities.AccountRole;
import me.inquis1tor.userservice.entities.AccountStatus;
import me.inquis1tor.userservice.validators.ExistsAccountValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.UUID;

/**
 * The annotated element must identify registered account.
 * <p>
 * Supported types are:
 * <ul>
 *     <li>{@link UUID}</li>
 * </ul>
 *
 * @author Alexander Sankevich
 */
@Target({ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistsAccountValidator.class)
public @interface ExistsAccount {

    AccountStatus status();

    AccountRole[] roles();

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
