package me.inqu1sitor.authservice.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import me.inqu1sitor.authservice.annotations.UniqueCredentials;
import me.inqu1sitor.authservice.dtos.CredentialsRequestDto;
import me.inqu1sitor.authservice.repositories.AccountRepository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class UniqueCredentialsValidator implements ConstraintValidator<UniqueCredentials, CredentialsRequestDto> {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public boolean isValid(CredentialsRequestDto value, ConstraintValidatorContext context) {
        return !accountRepository.existsByEmail(value.email());
    }
}
