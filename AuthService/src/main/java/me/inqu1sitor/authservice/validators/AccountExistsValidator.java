package me.inqu1sitor.authservice.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import me.inqu1sitor.authservice.annotations.AccountExists;
import me.inqu1sitor.authservice.entities.Account;
import me.inqu1sitor.authservice.repositories.AccountRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
public class AccountExistsValidator implements ConstraintValidator<AccountExists, UUID> {

    private Account.Role[] roles;
    private Account.Status status;

    private final AccountRepository accountRepository;

    @Override
    public void initialize(AccountExists constraintAnnotation) {
        roles=constraintAnnotation.roles();
        status=constraintAnnotation.status();
    }

    @Override
    @Transactional
    public boolean isValid(UUID value, ConstraintValidatorContext context) {
        return accountRepository.existsByIdAndStatusAndRoleIn(value, status, Set.of(roles));
    }
}
