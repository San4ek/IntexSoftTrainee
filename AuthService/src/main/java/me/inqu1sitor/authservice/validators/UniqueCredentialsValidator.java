package me.inqu1sitor.authservice.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.inqu1sitor.authservice.annotations.validation.UniqueCredentials;
import me.inqu1sitor.authservice.dtos.CredentialsRequestDto;
import me.inqu1sitor.authservice.repositories.AccountRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Defines the logic to validate a given constraint {@link UniqueCredentials}
 * for a given object type {@link CredentialsRequestDto}.
 *
 * @author AlexanderSankevich
 */
@Slf4j
@RequiredArgsConstructor
public class UniqueCredentialsValidator implements ConstraintValidator<UniqueCredentials, CredentialsRequestDto> {

    private final AccountRepository accountRepository;

    /**
     * Implements the validation logic.
     *
     * @param value   object to validate
     * @param context context in which the constraint is evaluated
     * @return {@code false} if {@code value} is {@code null} or contains already registered {@code email}
     */
    @Override
    @Transactional
    public boolean isValid(final CredentialsRequestDto value, ConstraintValidatorContext context) {
        log.info("Validating account credentials uniqueness");
        return !(value == null || accountRepository.existsByEmail(value.email()));
    }
}
