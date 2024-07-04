package me.inqu1sitor.authservice.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.inqu1sitor.authservice.annotations.validation.AccountExists;
import me.inqu1sitor.authservice.entities.Account;
import me.inqu1sitor.authservice.repositories.AccountRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class AccountExistsValidator implements ConstraintValidator<AccountExists, UUID> {

    private Account.Role[] roles;
    private Account.Status status;

    private final AccountRepository accountRepository;

    @Override
    public void initialize(AccountExists constraintAnnotation) {
        roles=constraintAnnotation.roles();
        status = constraintAnnotation.status();
    }

    @Override
    @Transactional
    public boolean isValid(UUID value, ConstraintValidatorContext context) {
        log.info("Validating account '{}' meeting the requirements", value);

        return accountRepository.existsByIdAndStatusAndRoleIn(value, status, Set.of(roles));
    }
}
