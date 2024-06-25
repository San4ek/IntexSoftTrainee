package me.inqu1sitor.authservice.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.inqu1sitor.authservice.annotations.validation.UniqueCredentials;
import me.inqu1sitor.authservice.dtos.CredentialsRequestDto;
import me.inqu1sitor.authservice.repositories.AccountRepository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class UniqueCredentialsValidator implements ConstraintValidator<UniqueCredentials, CredentialsRequestDto> {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public boolean isValid(CredentialsRequestDto value, ConstraintValidatorContext context) {
        log.info("Validating account credentials uniqueness");

        return !accountRepository.existsByEmail(value.email());
    }
}
