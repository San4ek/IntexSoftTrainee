package me.inqu1sitor.authservice.controllers.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.inqu1sitor.authservice.controllers.AccountController;
import me.inqu1sitor.authservice.dtos.CredentialsRequestDto;
import me.inqu1sitor.authservice.entities.Account;
import me.inqu1sitor.authservice.mappers.AccountMapper;
import me.inqu1sitor.authservice.services.AccountService;
import me.inqu1sitor.authservice.utils.LoggedAccountHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountControllerImpl implements AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final LoggedAccountHolder loggedAccountHolder;

    @Override
    public void registerUser(CredentialsRequestDto credentialsRequestDto) {
        log.info("Received request for new user registration");
        accountService.createAccount(accountMapper.credentialsToAccount(credentialsRequestDto), Account.Role.USER);
    }

    @Override
    public void registerModer(CredentialsRequestDto credentialsRequestDto) {
        log.info("Receive '{}' request for new moder registration", loggedAccountHolder.getId());
        accountService.createAccount(accountMapper.credentialsToAccount(credentialsRequestDto), Account.Role.MODER);
    }

    @Override
    public void registerAdmin(CredentialsRequestDto credentialsRequestDto) {
        log.info("Received '{}' request for new admin registration", loggedAccountHolder.getId());
        accountService.createAccount(accountMapper.credentialsToAccount(credentialsRequestDto), Account.Role.ADMIN);
    }

    @Override
    public void deleteAccount() {
        log.info("Received '{}' request for account deletion", loggedAccountHolder.getId());
        accountService.deleteAccount();
    }

    @Override
    public void blockAccount(UUID accountId) {
        log.info("Received '{}' request for account '{}' blocking", loggedAccountHolder.getId(), accountId);
        accountService.blockAccount(accountId);
    }

    @Override
    public void unblockAccount(UUID accountId) {
        log.info("Received '{}' request for account '{}' unblocking", loggedAccountHolder.getId(), accountId);
        accountService.unblockAccount(accountId);
    }

    @Override
    public void updateAccount(CredentialsRequestDto credentialsRequestDto) {
        log.info("Received '{}' request for updating credentials", loggedAccountHolder.getId());
        accountService.updateAccount(accountMapper.credentialsToAccount(credentialsRequestDto));
    }
}
