package me.inquis1tor.userservice.providers;

import me.inquis1tor.userservice.entities.AccountEntity;
import me.inquis1tor.userservice.entities.AccountRole;
import me.inquis1tor.userservice.entities.AccountStatus;
import me.inquis1tor.userservice.entities.PersonalInfoEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AccountEntityProvider {

    public AccountEntity activeUserEntity(final String accountId) {
        return correctEntity(accountId, AccountRole.USER, AccountStatus.ACTIVE);
    }

    public AccountEntity activeAdminEntity(final String accountId) {
        return correctEntity(accountId, AccountRole.ADMIN, AccountStatus.ACTIVE);
    }

    public AccountEntity activeModerEntity(final String accountId) {
        return correctEntity(accountId, AccountRole.MODER, AccountStatus.ACTIVE);
    }

    public AccountEntity blockedUserEntity(final String accountId) {
        return correctEntity(accountId, AccountRole.USER, AccountStatus.BLOCKED);
    }

    public AccountEntity blockedAdminEntity(final String accountId) {
        return correctEntity(accountId, AccountRole.ADMIN, AccountStatus.BLOCKED);
    }

    public AccountEntity blockedModerEntity(final String accountId) {
        return correctEntity(accountId, AccountRole.MODER, AccountStatus.BLOCKED);
    }

    public AccountEntity deletedUserEntity(final String accountId) {
        return correctEntity(accountId, AccountRole.USER, AccountStatus.DELETED);
    }

    public AccountEntity deletedAdminEntity(final String accountId) {
        return correctEntity(accountId, AccountRole.ADMIN, AccountStatus.DELETED);
    }

    public AccountEntity deletedModerEntity(final String accountId) {
        return correctEntity(accountId, AccountRole.MODER, AccountStatus.DELETED);
    }

    public AccountEntity correctEntity(final String accountId, final AccountRole role, final AccountStatus status) {
        PersonalInfoEntity personalInfoEntity = new PersonalInfoEntity();
        personalInfoEntity.setId(UUID.fromString(accountId));
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(UUID.fromString(accountId));
        accountEntity.setEmail("test@test.ru");
        accountEntity.setRole(role);
        accountEntity.setStatus(status);
        accountEntity.setPersonalInfo(personalInfoEntity);
        return accountEntity;
    }
}
