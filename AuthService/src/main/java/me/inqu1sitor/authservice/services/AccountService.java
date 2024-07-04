package me.inqu1sitor.authservice.services;

import me.inqu1sitor.authservice.annotations.validation.AccountExists;
import me.inqu1sitor.authservice.annotations.validation.UniqueAccount;
import me.inqu1sitor.authservice.dtos.CredentialsRequestDto;
import me.inqu1sitor.authservice.entities.AccountEntity;

import java.util.UUID;

public interface AccountService {

    void createAccount(@UniqueAccount CredentialsRequestDto credentialsRequestDto, final AccountEntity.Role role);

    void updateAccount(@UniqueAccount CredentialsRequestDto credentialsRequestDto);

    void blockAccount(@AccountExists(status = AccountEntity.Status.ACTIVE,
            roles = {AccountEntity.Role.USER, AccountEntity.Role.MODER}) final UUID accountId);

    void unblockAccount(@AccountExists(status = AccountEntity.Status.BLOCKED,
            roles = {AccountEntity.Role.USER, AccountEntity.Role.MODER}) final UUID accountId);

    void deleteAccount();
}
