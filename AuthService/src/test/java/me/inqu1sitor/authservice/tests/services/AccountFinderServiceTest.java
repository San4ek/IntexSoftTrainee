package me.inqu1sitor.authservice.tests.services;

import me.inqu1sitor.authservice.entities.AccountEntity;
import me.inqu1sitor.authservice.exceptions.AccountNotFoundException;
import me.inqu1sitor.authservice.repositories.AccountRepository;
import me.inqu1sitor.authservice.services.AccountFinderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

@SpringBootTest
@Testcontainers
class AccountFinderServiceTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    private AccountFinderService accountFinderService;
    @Autowired
    private AccountRepository accountRepository;

    @AfterEach
    void afterEach() {
        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("findActiveNotAdmin without entities in db")
    void findActiveNotAdminWithoutEntitiesInDb_ExceptionExpected() {
        final UUID accountId = UUID.randomUUID();
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountFinderService.findActiveNotAdmin(accountId));
    }

    @ParameterizedTest(name = "findActiveNotAdmin with incorrect {0} in db")
    @MethodSource({
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#blockedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#deletedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#notActiveAdminEntities"
    })
    void findActiveNotAdminWithIncorrectEntityInDb_ExceptionExpected(final AccountEntity accountEntity) {
        accountRepository.save(accountEntity);
        final UUID accountId = accountEntity.getId();
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountFinderService.findActiveNotAdmin(accountId));
    }

    @ParameterizedTest(name = "findActiveNotAdmin with correct {0} in db correctly")
    @MethodSource("me.inqu1sitor.authservice.providers.ArgumentsProvider#activeNotAdminEntities")
    void findActiveNotAdminWithCorrectEntityInDb_NotNullExpected(final AccountEntity accountEntity) {
        accountRepository.save(accountEntity);
        final AccountEntity optionalAccountEntity = accountFinderService.findActiveNotAdmin(accountEntity.getId());
        Assertions.assertNotNull(optionalAccountEntity);
        Assertions.assertEquals(accountEntity.getEmail(), optionalAccountEntity.getEmail());
        Assertions.assertEquals(accountEntity.getPassword(), optionalAccountEntity.getPassword());
        Assertions.assertEquals(accountEntity.getRole(), optionalAccountEntity.getRole());
        Assertions.assertEquals(accountEntity.getStatus(), optionalAccountEntity.getStatus());
    }

    @Test
    @DisplayName("findBlockedNotAdmin without entity in db")
    void findBlockedNotAdminWithoutEntitiesInDb_ExceptionExpected() {
        final UUID accountId = UUID.randomUUID();
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountFinderService.findBlockedNotAdmin(accountId));
    }

    @ParameterizedTest(name = "findBlockedNotAdmin with {1} entity in db")
    @MethodSource({
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeAdminEntity",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#deletedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#notActiveAdminEntities"
    })
    void findBlockedNotAdminWithIncorrectEntityInDb_ExceptionExpected(final AccountEntity accountEntity) {
        accountRepository.save(accountEntity);
        final UUID accountId = accountEntity.getId();
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountFinderService.findBlockedNotAdmin(accountId));
    }

    @ParameterizedTest(name = "findBlockedNotAdmin with correct {0} in db correctly")
    @MethodSource("me.inqu1sitor.authservice.providers.ArgumentsProvider#blockedNotAdminEntities")
    void findBlockedNotAdminWithCorrectEntityInDb_NotNullExpected(final AccountEntity accountEntity) {
        accountRepository.save(accountEntity);
        final AccountEntity optionalAccountEntity = accountFinderService.findBlockedNotAdmin(accountEntity.getId());
        Assertions.assertNotNull(optionalAccountEntity);
        Assertions.assertEquals(accountEntity.getEmail(), optionalAccountEntity.getEmail());
        Assertions.assertEquals(accountEntity.getPassword(), optionalAccountEntity.getPassword());
        Assertions.assertEquals(accountEntity.getRole(), optionalAccountEntity.getRole());
        Assertions.assertEquals(accountEntity.getStatus(), optionalAccountEntity.getStatus());
    }

    @Test
    @DisplayName("findActiveAny without entities in db")
    void findActiveAnyWithoutEntitiesInDb_ExceptionExpected() {
        final UUID accountId = UUID.randomUUID();
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountFinderService.findActiveAny(accountId));
    }

    @ParameterizedTest(name = "findActiveAny with incorrect {0} in db")
    @MethodSource({
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#blockedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#deletedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#notActiveAdminEntities"
    })
    void findActiveAnyWithIncorrectEntityInDb_ExceptionExpected(final AccountEntity accountEntity) {
        accountRepository.save(accountEntity);
        final UUID accountId = accountEntity.getId();
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountFinderService.findActiveAny(accountId));
    }

    @ParameterizedTest(name = "findActiveAny with correct {0} entity in db correctly")
    @MethodSource({
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeAdminEntity"
    })
    void findActiveAnyWithCorrectEntityInDb_NotNullExpected(final AccountEntity accountEntity) {
        accountRepository.save(accountEntity);
        final AccountEntity optionalAccountEntity = accountFinderService.findActiveAny(accountEntity.getId());
        Assertions.assertNotNull(optionalAccountEntity);
        Assertions.assertEquals(accountEntity.getEmail(), optionalAccountEntity.getEmail());
        Assertions.assertEquals(accountEntity.getPassword(), optionalAccountEntity.getPassword());
        Assertions.assertEquals(accountEntity.getRole(), optionalAccountEntity.getRole());
        Assertions.assertEquals(accountEntity.getStatus(), optionalAccountEntity.getStatus());
    }
}
