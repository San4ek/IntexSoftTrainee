package me.inquis1tor.userservice.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.inquis1tor.userservice.annotations.validation.ExistsAccount;
import me.inquis1tor.userservice.entities.AccountEntity;
import me.inquis1tor.userservice.repositories.AccountRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Defines the logic to validate a given constraint {@link ExistsAccount}
 * for a given object type {@link UUID}.
 *
 * @author AlexanderSankevich
 */
@Slf4j
@RequiredArgsConstructor
public class ExistsAccountValidator implements ConstraintValidator<ExistsAccount, UUID> {

    private AccountEntity.Status status;
    private AccountEntity.Role[] roles;

    private final AccountRepository accountRepository;

    /**
     * Initialize the validator with provided {@code roles} and {@code status} parameters.
     *
     * @param constraintAnnotation annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(ExistsAccount constraintAnnotation) {
        status = constraintAnnotation.status();
        roles = constraintAnnotation.roles();
    }

    /**
     * Implements the validation logic.
     *
     * @param value   object to validate
     * @param context context in which the constraint is evaluated
     * @return {@code false} if not exists {@link AccountEntity} identified by the
     * provided parameter {@code value} and {@code roles} and {@code status} parameters
     */
    @Override
    @Transactional
    public boolean isValid(final UUID value, ConstraintValidatorContext context) {
        log.info("Validating account '{}' meeting the requirements", value);
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Account '"+value+"' not exists or not meet the requirements").addConstraintViolation();
        return accountRepository.existsByIdAndStatusAndRoleIn(value, status, List.of(roles));
    }
}
