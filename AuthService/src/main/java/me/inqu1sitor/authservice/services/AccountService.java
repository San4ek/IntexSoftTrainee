package me.inqu1sitor.authservice.services;

import me.inqu1sitor.authservice.annotations.validation.AccountExists;
import me.inqu1sitor.authservice.annotations.validation.UniqueCredentials;
import me.inqu1sitor.authservice.dtos.CredentialsRequestDto;
import me.inqu1sitor.authservice.entities.Account;

import java.util.UUID;

public interface AccountService {

    void createAccount(@UniqueCredentials Account credentials, Account.Role role);

    void updateAccount(@UniqueCredentials Account credentials);

    void blockAccount(@AccountExists(status = Account.Status.ACTIVE,
            roles = {Account.Role.USER, Account.Role.MODER}) UUID accountId);

    void unblockAccount(@AccountExists(status = Account.Status.BLOCKED,
            roles = {Account.Role.USER, Account.Role.MODER}) UUID accountId);

    void deleteAccount();
}
