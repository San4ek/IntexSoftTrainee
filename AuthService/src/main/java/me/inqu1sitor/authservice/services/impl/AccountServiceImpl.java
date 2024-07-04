package me.inqu1sitor.authservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.inqu1sitor.authservice.clients.UserServiceClient;
import me.inqu1sitor.authservice.dtos.CredentialsRequestDto;
import me.inqu1sitor.authservice.entities.AccountEntity;
import me.inqu1sitor.authservice.mappers.AccountMapper;
import me.inqu1sitor.authservice.rabbit.AccountDeletedNotifier;
import me.inqu1sitor.authservice.repositories.AccountRepository;
import me.inqu1sitor.authservice.services.AccountFinderService;
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
    private final AccountFinderService accountFinderService;
    private final UserServiceClient userServiceClient;
    private final AccountMapper accountMapper;

    @Override
    @Transactional
    public void createAccount(CredentialsRequestDto credentialsRequestDto, AccountEntity.Role role) {
        log.info("Creating {} account", role);
        AccountEntity accountEntity = accountMapper.credentialsToAccount(credentialsRequestDto);
        accountEntity.setPassword(passwordEncoder.encode(accountEntity.getPassword()));
        accountEntity.setRole(role);
        accountEntity.setStatus(AccountEntity.Status.ACTIVE);
        accountRepository.save(accountEntity);
        userServiceClient.register(accountMapper.accountToTransferDto(accountEntity));
        log.info("Account created");
    }

    @Override
    @Transactional
    public void updateAccount(CredentialsRequestDto credentialsRequestDto) {
        log.info("Updating account '{}'", loggedAccountHolder.getId());
        AccountEntity accountEntity = accountFinderService.findActiveAny(loggedAccountHolder.getId());
        accountEntity.setPassword(passwordEncoder.encode(credentialsRequestDto.password()));
        accountEntity.setEmail(credentialsRequestDto.email());
        accountEntity = accountRepository.save(accountEntity);
        userServiceClient.update(accountMapper.accountToCredentialsTransferDto(accountEntity));
        log.info("Account '{}' updated", loggedAccountHolder.getId());
    }

    @Override
    @Transactional
    public void blockAccount(UUID accountId) {
        log.info("Blocking account '{}'", loggedAccountHolder.getId());
        AccountEntity accountEntity = accountFinderService.findActiveNotAdmin(accountId);
        accountEntity.setId(accountId);
        accountEntity.setStatus(AccountEntity.Status.BLOCKED);
        accountRepository.save(accountEntity);
        userServiceClient.block(accountId, loggedAccountHolder.getId());
        log.info("Account '{}' blocked", loggedAccountHolder.getId());
    }

    @Override
    @Transactional
    public void unblockAccount(UUID accountId) {
        log.info("Unblocking account '{}'", loggedAccountHolder.getId());
        AccountEntity accountEntity = accountFinderService.findBlockedNotAdmin(accountId);
        accountEntity.setId(accountId);
        accountEntity.setStatus(AccountEntity.Status.ACTIVE);
        accountRepository.save(accountEntity);
        userServiceClient.unblock(accountId);
        log.info("Account '{}' unblocked", loggedAccountHolder.getId());
    }

    @Override
    public void deleteAccount() {
        log.info("Deleting account '{}'", loggedAccountHolder.getId());
        AccountEntity accountEntity = accountRepository.findById(loggedAccountHolder.getId()).get();
        accountEntity.setStatus(AccountEntity.Status.DELETED);
        accountRepository.saveAndFlush(accountEntity);
        log.info("Account '{}' deleted", loggedAccountHolder.getId());
        accountDeletedNotifier.notifyAbout(loggedAccountHolder.getId());
    }
}
