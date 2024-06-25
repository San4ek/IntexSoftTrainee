package me.inquis1tor.userservice.services;

import me.inquis1tor.userservice.annotations.validation.uuid.AdminActiveUuid;
import me.inquis1tor.userservice.annotations.validation.uuid.UserOrModerActiveUuid;
import me.inquis1tor.userservice.annotations.validation.uuid.UserOrModerBlockedUuid;
import me.inquis1tor.userservice.annotations.validation.credentials.UniqueAccount;
import me.inquis1tor.userservice.entities.Account;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Validated
public interface AccountService {

    boolean existsByIdAndStatusAndRoles(UUID id, Account.Status status, Account.Role[] role);

    void createAccount(@UniqueAccount Account account);

    Account getAccount();

    List<Account> getAll();

    void delete(@UserOrModerActiveUuid UUID accountId);

    void block(@UserOrModerActiveUuid UUID accountId, @AdminActiveUuid UUID adminId);

    void unblock(@UserOrModerBlockedUuid UUID accountId);

    boolean existByEmail(String email);
}
