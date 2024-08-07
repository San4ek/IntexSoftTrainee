package me.inquis1tor.userservice.services;

import me.inquis1tor.userservice.annotations.validation.ExistsAccount;
import me.inquis1tor.userservice.annotations.validation.UniqueCredentials;
import me.inquis1tor.userservice.dtos.AccountResponseDto;
import me.inquis1tor.userservice.dtos.AccountTransferDto;
import me.inquis1tor.userservice.dtos.CredentialsTransferDto;
import me.inquis1tor.userservice.entities.AccountEntity;
import me.inquis1tor.userservice.entities.AccountRole;
import me.inquis1tor.userservice.entities.AccountStatus;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

/**
 * Implementations of this interface are responsible for the management
 * of {@link AccountEntity}.
 *
 * @author Alexander Sankevich
 */
@Validated
public interface AccountService {

    void createAccount(@UniqueCredentials AccountTransferDto dto);

    AccountResponseDto getAccount();

    List<AccountResponseDto> getAll();

    void deleteAccount(UUID accountId);

    void blockAccount(UUID accountId, @ExistsAccount(roles = AccountRole.ADMIN,
            status = AccountStatus.ACTIVE) UUID adminId);

    void unblockAccount(UUID accountId);

    void updateCredentials(@UniqueCredentials CredentialsTransferDto dto);

    String getEmail(UUID accountId);
}
