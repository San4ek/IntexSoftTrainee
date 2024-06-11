package me.inquis1tor.userservice.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import me.inquis1tor.userservice.annotations.UniqueEmail;
import me.inquis1tor.userservice.entities.Credentials;
import me.inquis1tor.userservice.services.CredentialsService;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, Credentials> {

    private CredentialsService credentialsService;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    @Transactional
    public boolean isValid(Credentials value, ConstraintValidatorContext context) {
        return !credentialsService.existsByEmail(value);
    }
}
