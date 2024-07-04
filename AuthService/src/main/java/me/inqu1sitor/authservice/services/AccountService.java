package me.inqu1sitor.authservice.services;

import me.inqu1sitor.authservice.annotations.validation.AccountExists;
import me.inqu1sitor.authservice.annotations.validation.UniqueAccount;
import me.inqu1sitor.authservice.entities.Account;

import java.util.UUID;

public interface AccountService {

    void createAccount(@UniqueAccount Account account, final Account.Role role);

    void updateAccount(@UniqueAccount Account account);

    void blockAccount(@AccountExists(status = Account.Status.ACTIVE,
            roles = {Account.Role.USER, Account.Role.MODER}) final UUID accountId);

    void unblockAccount(@AccountExists(status = Account.Status.BLOCKED,
            roles = {Account.Role.USER, Account.Role.MODER}) final UUID accountId);

    void deleteAccount();
}
