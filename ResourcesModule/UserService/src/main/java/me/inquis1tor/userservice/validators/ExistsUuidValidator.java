package me.inquis1tor.userservice.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import me.inquis1tor.userservice.annotations.validation.uuid.ExistsUuidWithParams;
import me.inquis1tor.userservice.entities.Account;
import me.inquis1tor.userservice.services.impl.AccountServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
public class ExistsUuidValidator implements ConstraintValidator<ExistsUuidWithParams, UUID> {

    private Account.Status status;
    private Account.Role[] role;

    private final AccountServiceImpl accountServiceImpl;

    @Override
    public void initialize(ExistsUuidWithParams constraintAnnotation) {
        status=constraintAnnotation.status();
        role=constraintAnnotation.roles();
    }

    @Override
    @Transactional
    public boolean isValid(UUID value, ConstraintValidatorContext context) {
         return accountServiceImpl.existsByIdAndStatusAndRoles(value, status, role);
    }
}
