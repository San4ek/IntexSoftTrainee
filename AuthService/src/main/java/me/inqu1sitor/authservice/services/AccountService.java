package me.inqu1sitor.authservice.services;

import jakarta.validation.constraints.NotNull;
import me.inqu1sitor.authservice.annotations.validation.UniqueCredentials;
import me.inqu1sitor.authservice.dtos.CredentialsRequestDto;
import me.inqu1sitor.authservice.entities.AccountEntity;
import me.inqu1sitor.authservice.entities.AccountRole;

import java.util.UUID;

/**
 * Implementations of this interface are responsible for the management
 * of {@link AccountEntity}.
 *
 * @author Alexander Sankevich
 */
public interface AccountService {

    void createAccount(@UniqueCredentials CredentialsRequestDto credentialsRequestDto, @NotNull AccountRole role);

    void updateAccount(@UniqueCredentials CredentialsRequestDto credentialsRequestDto);

    void blockAccount(UUID accountId);

    void unblockAccount(UUID accountId);

    void deleteAccount();
}
