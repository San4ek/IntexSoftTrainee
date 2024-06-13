package me.inquis1tor.userservice.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import me.inquis1tor.userservice.annotations.UniqueCredentials;
import me.inquis1tor.userservice.entities.Credentials;
import me.inquis1tor.userservice.exceptions.EmailAlreadyExistsException;
import me.inquis1tor.userservice.services.CredentialsService;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class UniqueCredentialsValidator implements ConstraintValidator<UniqueCredentials, Credentials> {

    private final CredentialsService credentialsService;

    @Override
    @Transactional
    public boolean isValid(Credentials value, ConstraintValidatorContext context) {
        return !credentialsService.existsByEmail(value);
    }
}
