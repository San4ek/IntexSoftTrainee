package me.inqu1sitor.authservice.services.impl;

import lombok.RequiredArgsConstructor;
import me.inqu1sitor.authservice.annotations.validation.UniqueCredentials;
import me.inqu1sitor.authservice.dtos.CredentialsRequestDto;
import me.inqu1sitor.authservice.entities.Account;
import me.inqu1sitor.authservice.mappers.AccountMapper;
import me.inqu1sitor.authservice.rabbit.AccountDeletedNotifier;
import me.inqu1sitor.authservice.repositories.AccountRepository;
import me.inqu1sitor.authservice.services.AccountService;
import me.inqu1sitor.authservice.utils.LoggedAccountHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoggedAccountHolder loggedAccountHolder;
    private final AccountDeletedNotifier accountDeletedNotifier;

    @Override
    @Transactional
    public void createAccount(CredentialsRequestDto credentials, Account.Role role) {
        Account account = accountMapper.credentialsToAccount(credentials);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRole(role);
        account.setStatus(Account.Status.ACTIVE);
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void updateAccount(CredentialsRequestDto credentials) {
        Account account=accountMapper.credentialsToAccount(credentials);
        account.setId(loggedAccountHolder.getId());
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void blockAccount(UUID accountId) {
        Account account = new Account();
        account.setId(accountId);
        account.setStatus(Account.Status.BLOCKED);
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void unblockAccount(UUID accountId) {
        Account account = new Account();
        account.setId(accountId);
        account.setStatus(Account.Status.ACTIVE);
        accountRepository.save(account);
    }

    @Override
    public void deleteAccount() {
        accountRepository.deleteById(loggedAccountHolder.getId());
        accountDeletedNotifier.notifyAbout(loggedAccountHolder.getId());
    }
}
