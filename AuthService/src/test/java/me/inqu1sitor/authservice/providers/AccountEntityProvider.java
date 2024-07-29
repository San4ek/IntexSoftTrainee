package me.inqu1sitor.authservice.providers;

import me.inqu1sitor.authservice.entities.AccountEntity;
import me.inqu1sitor.authservice.entities.AccountRole;
import me.inqu1sitor.authservice.entities.AccountStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AccountEntityProvider {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static AccountEntity activeUserEntity() {
        return correctEntity(AccountRole.USER, AccountStatus.ACTIVE);
    }

    public static AccountEntity activeAdminEntity() {
        return correctEntity(AccountRole.ADMIN, AccountStatus.ACTIVE);
    }

    public static AccountEntity activeModerEntity() {
        return correctEntity(AccountRole.MODER, AccountStatus.ACTIVE);
    }

    public static AccountEntity blockedUserEntity() {
        return correctEntity(AccountRole.USER, AccountStatus.BLOCKED);
    }

    public static AccountEntity blockedAdminEntity() {
        return correctEntity(AccountRole.ADMIN, AccountStatus.BLOCKED);
    }

    public static AccountEntity blockedModerEntity() {
        return correctEntity(AccountRole.MODER, AccountStatus.BLOCKED);
    }

    public static AccountEntity deletedUserEntity() {
        return correctEntity(AccountRole.USER, AccountStatus.DELETED);
    }

    public static AccountEntity deletedAdminEntity() {
        return correctEntity(AccountRole.ADMIN, AccountStatus.DELETED);
    }

    public static AccountEntity deletedModerEntity() {
        return correctEntity(AccountRole.MODER, AccountStatus.DELETED);
    }

    public static AccountEntity correctEntity(final AccountRole role, final AccountStatus status) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setEmail("test@test.ru");
        accountEntity.setRole(role);
        accountEntity.setStatus(status);
        accountEntity.setPassword(passwordEncoder.encode("123456"));
        return accountEntity;
    }
}
