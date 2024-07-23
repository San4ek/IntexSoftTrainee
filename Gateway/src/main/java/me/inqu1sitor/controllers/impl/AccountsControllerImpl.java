package me.inqu1sitor.controllers.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.inqu1sitor.clients.AuthAccountsClient;
import me.inqu1sitor.clients.UserAccountsClient;
import me.inqu1sitor.controllers.AccountsController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Accounts", description = "Account management APIs")
public class AccountsControllerImpl implements AccountsController {

    private final AuthAccountsClient authAccountsClient;
    private final UserAccountsClient userAccountsClient;

    @Override
    public ResponseEntity<Object> getAccount() {
        return userAccountsClient.getAccount();
    }

    @Override
    public ResponseEntity<Object> blockAccount(final UUID accountId) {
        return authAccountsClient.blockAccount(accountId);
    }

    @Override
    public ResponseEntity<Object> getAllAccounts() {
        return userAccountsClient.getAllAccount();
    }

    @Override
    public ResponseEntity<Object> unblockAccount(final UUID accountId) {
        return authAccountsClient.unblockAccount(accountId);
    }

    @Override
    public ResponseEntity<Object> deleteAccount() {
        return authAccountsClient.deleteAccount();
    }

    @Override
    public ResponseEntity<Object> logout() {
        return authAccountsClient.logout();
    }

    @Override
    public ResponseEntity<Object> logoutAll() {
        return authAccountsClient.logoutAll();
    }
}
