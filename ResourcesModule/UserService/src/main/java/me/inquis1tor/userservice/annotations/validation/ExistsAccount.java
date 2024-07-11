package me.inquis1tor.userservice.annotations.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import me.inquis1tor.userservice.entities.AccountEntity;
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

    AccountEntity.Status status();

    AccountEntity.Role[] roles();

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
