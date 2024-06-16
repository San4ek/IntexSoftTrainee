package me.inquis1tor.userservice.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import me.inquis1tor.userservice.annotations.validation.credentials.UniqueCredentials;
import me.inquis1tor.userservice.entities.Credentials;
import me.inquis1tor.userservice.services.impl.CredentialsServiceImpl;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class UniqueCredentialsValidator implements ConstraintValidator<UniqueCredentials, Credentials> {

    private final CredentialsServiceImpl credentialsServiceImpl;

    @Override
    @Transactional
    public boolean isValid(Credentials value, ConstraintValidatorContext context) {
        return !credentialsServiceImpl.existsByEmail(value);
    }
}
