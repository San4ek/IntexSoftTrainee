package me.inquis1tor.userservice.services;

import me.inquis1tor.userservice.annotations.validation.uuid.UserOrModerActiveUuid;
import me.inquis1tor.userservice.annotations.validation.uuid.UserOrModerBlockedUuid;
import me.inquis1tor.userservice.annotations.validation.credentials.UniqueCredentials;
import me.inquis1tor.userservice.entities.Account;
import me.inquis1tor.userservice.entities.Credentials;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Validated
public interface AccountService {

    boolean existsByIdAndStatusAndRoles(UUID id, Account.Status status, Account.Role[] role);

    void createAccount(@UniqueCredentials Credentials credentials);

    Account get(UUID accountId);

    Account get(String email);

    List<Account> getAll();

    void delete(@UserOrModerActiveUuid UUID accountId);

    Account block(@UserOrModerActiveUuid UUID accountId, UUID adminId);

    Account unblock(@UserOrModerBlockedUuid UUID accountId, UUID adminId);
}
