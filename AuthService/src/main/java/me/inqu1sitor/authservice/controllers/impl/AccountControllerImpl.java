package me.inqu1sitor.authservice.controllers.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.inqu1sitor.authservice.controllers.AccountController;
import me.inqu1sitor.authservice.dtos.CredentialsRequestDto;
import me.inqu1sitor.authservice.entities.AccountRole;
import me.inqu1sitor.authservice.services.AccountService;
import me.inqu1sitor.authservice.services.LogoutService;
import me.inqu1sitor.authservice.utils.LoggedAccountDetailsProvider;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountControllerImpl implements AccountController {

    private final LogoutService logoutService;
    private final AccountService accountService;
    private final LoggedAccountDetailsProvider loggedAccountDetailsProvider;

    @Override
    public void registerUser(CredentialsRequestDto credentialsRequestDto) {
        log.info("Received request for new user registration");
        accountService.createAccount(credentialsRequestDto, AccountRole.USER);
    }

    @Override
    public void registerModer(CredentialsRequestDto credentialsRequestDto) {
        log.info("Receive '{}' request for new moder registration", loggedAccountDetailsProvider.getAccountId());
        accountService.createAccount(credentialsRequestDto, AccountRole.MODER);
    }

    @Override
    public void registerAdmin(CredentialsRequestDto credentialsRequestDto) {
        log.info("Received '{}' request for new admin registration", loggedAccountDetailsProvider.getAccountId());
        accountService.createAccount(credentialsRequestDto, AccountRole.ADMIN);
    }

    @Override
    public void deleteAccount() {
        log.info("Received '{}' request for account deletion", loggedAccountDetailsProvider.getAccountId());
        accountService.deleteAccount();
    }

    @Override
    public void blockAccount(UUID accountId) {
        log.info("Received '{}' request for account '{}' blocking", loggedAccountDetailsProvider.getAccountId(), accountId);
        accountService.blockAccount(accountId);
    }

    @Override
    public void unblockAccount(UUID accountId) {
        log.info("Received '{}' request for account '{}' unblocking", loggedAccountDetailsProvider.getAccountId(), accountId);
        accountService.unblockAccount(accountId);
    }

    @Override
    public void updateAccount(CredentialsRequestDto credentialsRequestDto) {
        log.info("Received '{}' request for updating credentials", loggedAccountDetailsProvider.getAccountId());
        accountService.updateAccount(credentialsRequestDto);
    }

    @Override
    public void logout() {
        log.info("Received '{}' request for logout", loggedAccountDetailsProvider.getAccountId());
        logoutService.logout();
    }

    @Override
    public void logoutAll() {
        log.info("Received '{}' request for logout from all devices", loggedAccountDetailsProvider.getAccountId());
        logoutService.logoutAll();
    }
}
