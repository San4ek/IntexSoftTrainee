package me.inqu1sitor.authservice.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.inqu1sitor.authservice.annotations.validation.UniqueAccount;
import me.inqu1sitor.authservice.entities.Account;
import me.inqu1sitor.authservice.repositories.AccountRepository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class UniqueAccountValidator implements ConstraintValidator<UniqueAccount, Account> {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public boolean isValid(Account value, ConstraintValidatorContext context) {
        log.info("Validating account credentials uniqueness");
        return !accountRepository.existsByEmail(value.getEmail());
    }
}
