package me.inquis1tor.userservice.services;

import me.inquis1tor.userservice.annotations.ActiveAccountUuid;
import me.inquis1tor.userservice.annotations.ActiveAdminUuid;
import me.inquis1tor.userservice.annotations.BlockedAccountUuid;
import me.inquis1tor.userservice.annotations.UniqueCredentials;
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

    void delete(@ActiveAccountUuid UUID accountId);

    void block(@ActiveAccountUuid UUID accountId,
               @ActiveAdminUuid UUID adminId);

    void unblock(@BlockedAccountUuid UUID accountId,
                        @ActiveAdminUuid UUID adminId);
}
