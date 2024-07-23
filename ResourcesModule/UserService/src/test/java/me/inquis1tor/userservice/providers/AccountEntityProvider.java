package me.inquis1tor.userservice.providers;

import me.inquis1tor.userservice.entities.AccountEntity;
import me.inquis1tor.userservice.entities.PersonalInfoEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AccountEntityProvider {

    public AccountEntity activeUserEntity(final String accountId) {
        return correctEntity(accountId, AccountEntity.Role.USER, AccountEntity.Status.ACTIVE);
    }

    public AccountEntity activeAdminEntity(final String accountId) {
        return correctEntity(accountId, AccountEntity.Role.ADMIN, AccountEntity.Status.ACTIVE);
    }

    public AccountEntity activeModerEntity(final String accountId) {
        return correctEntity(accountId, AccountEntity.Role.MODER, AccountEntity.Status.ACTIVE);
    }

    public AccountEntity blockedUserEntity(final String accountId) {
        return correctEntity(accountId, AccountEntity.Role.USER, AccountEntity.Status.BLOCKED);
    }

    public AccountEntity blockedAdminEntity(final String accountId) {
        return correctEntity(accountId, AccountEntity.Role.ADMIN, AccountEntity.Status.BLOCKED);
    }

    public AccountEntity blockedModerEntity(final String accountId) {
        return correctEntity(accountId, AccountEntity.Role.MODER, AccountEntity.Status.BLOCKED);
    }

    public AccountEntity deletedUserEntity(final String accountId) {
        return correctEntity(accountId, AccountEntity.Role.USER, AccountEntity.Status.DELETED);
    }

    public AccountEntity deletedAdminEntity(final String accountId) {
        return correctEntity(accountId, AccountEntity.Role.ADMIN, AccountEntity.Status.DELETED);
    }

    public AccountEntity deletedModerEntity(final String accountId) {
        return correctEntity(accountId, AccountEntity.Role.MODER, AccountEntity.Status.DELETED);
    }

    public AccountEntity correctEntity(final String accountId, final AccountEntity.Role role, final AccountEntity.Status status) {
        PersonalInfoEntity personalInfoEntity = new PersonalInfoEntity();
        personalInfoEntity.setId(UUID.fromString(accountId));
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(UUID.fromString(accountId));
        accountEntity.setEmail("test@test.ru");
        accountEntity.setRole(role);
        accountEntity.setStatus(status);
        accountEntity.setPersonalInfoEntity(personalInfoEntity);
        return accountEntity;
    }
}
