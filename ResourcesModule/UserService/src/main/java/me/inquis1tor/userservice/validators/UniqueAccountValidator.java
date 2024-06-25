package me.inquis1tor.userservice.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import me.inquis1tor.userservice.annotations.validation.credentials.UniqueAccount;
import me.inquis1tor.userservice.entities.Account;
import me.inquis1tor.userservice.services.AccountService;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class UniqueAccountValidator implements ConstraintValidator<UniqueAccount, Account> {

    private final AccountService accountService;

    @Override
    @Transactional
    public boolean isValid(Account value, ConstraintValidatorContext context) {


        return !accountService.existByEmail(value.getEmail());
    }
}
