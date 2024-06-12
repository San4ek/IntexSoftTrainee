package me.inquis1tor.userservice.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import me.inquis1tor.userservice.annotations.ExistsUuid;
import me.inquis1tor.userservice.entities.Account;
import me.inquis1tor.userservice.exceptions.AccountNotExistsException;
import me.inquis1tor.userservice.services.AccountService;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
public class ExistsUuidValidator implements ConstraintValidator<ExistsUuid, UUID> {

    private String message;
    private String parameter;
    private Account.Status status;
    private Account.Role[] role;

    private final AccountService accountService;

    @Override
    public void initialize(ExistsUuid constraintAnnotation) {
        status=constraintAnnotation.status();
        role=constraintAnnotation.role();
        message= constraintAnnotation.message();
        parameter = constraintAnnotation.parameter();
    }

    @Override
    @Transactional
    public boolean isValid(UUID value, ConstraintValidatorContext context) {
         if (!accountService.existsByIdAndStatusAndRole(value, status, role))
             throw new AccountNotExistsException(parameter,message);

         return true;
    }
}
