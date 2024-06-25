package me.inqu1sitor.authservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.inqu1sitor.authservice.entities.Account;
import me.inqu1sitor.authservice.rabbit.AccountDeletedNotifier;
import me.inqu1sitor.authservice.repositories.AccountRepository;
import me.inqu1sitor.authservice.services.AccountService;
import me.inqu1sitor.authservice.utils.LoggedAccountHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Slf4j
@Validated
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoggedAccountHolder loggedAccountHolder;
    private final AccountDeletedNotifier accountDeletedNotifier;

    @Override
    @Transactional
    public void createAccount(Account account, Account.Role role) {
        log.info("Creating {} account", role);

        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRole(role);
        account.setStatus(Account.Status.ACTIVE);
        accountRepository.save(account);

        log.info("Account created");
    }

    @Override
    @Transactional
    public void updateAccount(Account account) {
        log.info("Updating account '{}'", loggedAccountHolder.getId());

        account.setId(loggedAccountHolder.getId());
        accountRepository.save(account);

        log.info("Account '{}' updated", loggedAccountHolder.getId());
    }

    @Override
    @Transactional
    public void blockAccount(UUID accountId) {
        log.info("Blocking account '{}'", loggedAccountHolder.getId());

        Account account = new Account();
        account.setId(accountId);
        account.setStatus(Account.Status.BLOCKED);
        accountRepository.save(account);

        log.info("Account '{}' blocked", loggedAccountHolder.getId());
    }

    @Override
    @Transactional
    public void unblockAccount(UUID accountId) {
        log.info("Unblocking account '{}'", loggedAccountHolder.getId());

        Account account = new Account();
        account.setId(accountId);
        account.setStatus(Account.Status.ACTIVE);
        accountRepository.save(account);

        log.info("Account '{}' unblocked", loggedAccountHolder.getId());
    }

    @Override
    public void deleteAccount() {
        log.info("Deleting account '{}'", loggedAccountHolder.getId());

        accountRepository.deleteById(loggedAccountHolder.getId());

        log.info("Account '{}' deleted", loggedAccountHolder.getId());

        accountDeletedNotifier.notifyAbout(loggedAccountHolder.getId());
    }
}
