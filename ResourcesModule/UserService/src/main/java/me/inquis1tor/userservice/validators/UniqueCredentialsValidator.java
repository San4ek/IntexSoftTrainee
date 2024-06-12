package me.inquis1tor.userservice.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import me.inquis1tor.userservice.annotations.UniqueCredentials;
import me.inquis1tor.userservice.entities.Credentials;
import me.inquis1tor.userservice.exceptions.EmailAlreadyExistsException;
import me.inquis1tor.userservice.services.CredentialsService;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
public class UniqueCredentialsValidator implements ConstraintValidator<UniqueCredentials, Credentials> {

    private CredentialsService credentialsService;

    private String parameter;
    private String message;

    @Override
    public void initialize(UniqueCredentials constraintAnnotation) {
        this.parameter=constraintAnnotation.parameter();
        this.message=constraintAnnotation.message();
    }

    @Override
    @Transactional
    public boolean isValid(Credentials value, ConstraintValidatorContext context) {
        if (credentialsService.existsByEmail(value))
            throw new EmailAlreadyExistsException(parameter, message);

        return true;
    }
}
