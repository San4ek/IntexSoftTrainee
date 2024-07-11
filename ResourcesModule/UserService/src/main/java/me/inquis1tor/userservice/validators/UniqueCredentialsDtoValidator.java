package me.inquis1tor.userservice.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.inquis1tor.userservice.annotations.validation.UniqueCredentials;
import me.inquis1tor.userservice.dtos.AccountTransferDto;
import me.inquis1tor.userservice.dtos.CredentialsTransferDto;
import me.inquis1tor.userservice.repositories.AccountRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Defines the logic to validate a given constraint {@link UniqueCredentials}
 * for a given object type {@link CredentialsTransferDto}.
 *
 * @author AlexanderSankevich
 */
@Slf4j
@RequiredArgsConstructor
public class UniqueCredentialsDtoValidator implements ConstraintValidator<UniqueCredentials, CredentialsTransferDto> {

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
    public boolean isValid(final CredentialsTransferDto value, ConstraintValidatorContext context) {
        log.info("Validating credentials for uniqueness");
        return !(value == null || accountRepository.existsByEmail(value.email()));
    }
}
