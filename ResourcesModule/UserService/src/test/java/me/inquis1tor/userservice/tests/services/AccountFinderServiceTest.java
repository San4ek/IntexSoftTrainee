package me.inquis1tor.userservice.tests.services;

import me.inquis1tor.userservice.entities.AccountEntity;
import me.inquis1tor.userservice.exceptions.AccountNotFoundException;
import me.inquis1tor.userservice.providers.AccountEntityProvider;
import me.inquis1tor.userservice.repositories.AccountRepository;
import me.inquis1tor.userservice.services.AccountFinderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;
import java.util.stream.Stream;

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
    private static final AccountEntityProvider accountEntityProvider = new AccountEntityProvider();

    @AfterEach
    void afterEach() {
        accountRepository.deleteAll();
    }

    private static Stream<Arguments> activeNotAdminEntities() {
        return Stream.of(
                Arguments.of(accountEntityProvider.activeUserEntity("c0a80065-90a2-1cb0-8190-a20de91f0000"), "active user"),
                Arguments.of(accountEntityProvider.activeModerEntity("c0a80065-90a2-1cb0-8190-a20de91f0000"), "active moder")
        );
    }

    private static Stream<Arguments> blockedNotAdminEntities() {
        return Stream.of(
                Arguments.of(accountEntityProvider.blockedUserEntity("c0a80065-90a2-1cb0-8190-a20de91f0000"), "blocked user"),
                Arguments.of(accountEntityProvider.blockedModerEntity("c0a80065-90a2-1cb0-8190-a20de91f0000"), "blocked moder")
        );
    }

    private static Stream<Arguments> deletedNotAdminEntities() {
        return Stream.of(
                Arguments.of(accountEntityProvider.deletedUserEntity("c0a80065-90a2-1cb0-8190-a20de91f0000"), "deleted user"),
                Arguments.of(accountEntityProvider.deletedModerEntity("c0a80065-90a2-1cb0-8190-a20de91f0000"), "deleted moder")
        );
    }

    private static Stream<Arguments> notActiveAdminEntities() {
        return Stream.of(
                Arguments.of(accountEntityProvider.blockedAdminEntity("c0a80065-90a2-1cb0-8190-a20de91f0000"), "blocked admin"),
                Arguments.of(accountEntityProvider.deletedAdminEntity("c0a80065-90a2-1cb0-8190-a20de91f0000"), "deleted admin")
        );
    }

    private static Stream<Arguments> activeAdminEntity() {
        return Stream.of(
                Arguments.of(accountEntityProvider.activeAdminEntity("c0a80065-90a2-1cb0-8190-a20de91f0000"), "blocked admin")
        );
    }

    @Test
    @DisplayName("findActiveNotAdmin without entity in db")
    void findActiveNotAdminWithoutEntitiesInDb_ExceptionExpected() {
        UUID accountId = UUID.randomUUID();
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountFinderService.findActiveNotAdmin(accountId));
    }

    @ParameterizedTest(name = "findActiveNotAdmin with {1} entity in db")
    @MethodSource({"blockedNotAdminEntities", "deletedNotAdminEntities", "notActiveAdminEntities"})
    void findActiveNotAdminWithIncorrectUserEntityInDb_ExceptionExpected(final AccountEntity accountEntity, final String description) {
        accountRepository.save(accountEntity);
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountFinderService.findActiveNotAdmin(accountEntity.getId()));
    }

    @ParameterizedTest(name = "findActiveNotAdmin with {1} entity in db")
    @MethodSource("activeNotAdminEntities")
    void findActiveNotAdminWithCorrectEntityInDb_NotNullExpected(final AccountEntity accountEntity, final String description) {
        accountRepository.save(accountEntity);
        Assertions.assertNotNull(accountFinderService.findActiveNotAdmin(accountEntity.getId()));
    }

    @Test
    @DisplayName("findBlockedNotAdmin without entity in db")
    void findBlockedNotAdminWithoutEntitiesInDb_ExceptionExpected() {
        UUID accountId = UUID.randomUUID();
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountFinderService.findBlockedNotAdmin(accountId));
    }

    @ParameterizedTest(name = "findBlockedNotAdmin with {1} entity in db")
    @MethodSource({"activeNotAdminEntities", "activeAdminEntity", "deletedNotAdminEntities", "notActiveAdminEntities"})
    void findBlockedNotAdminWithIncorrectEntityInDb_ExceptionExpected(final AccountEntity accountEntity, final String description) {
        accountRepository.save(accountEntity);
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountFinderService.findBlockedNotAdmin(accountEntity.getId()));
    }

    @ParameterizedTest(name = "findBlockedNotAdmin with {1} entity in db")
    @MethodSource("blockedNotAdminEntities")
    void findBlockedNotAdminWithCorrectEntityInDb_NotNullExpected(final AccountEntity accountEntity, final String description) {
        accountRepository.save(accountEntity);
        Assertions.assertNotNull(accountFinderService.findBlockedNotAdmin(accountEntity.getId()));
    }

    @Test
    @DisplayName("findActiveAny without entity in db")
    void findActiveAnyWithoutEntitiesInDb_ExceptionExpected() {
        UUID accountId = UUID.randomUUID();
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountFinderService.findActiveAny(accountId));
    }

    @ParameterizedTest(name = "findActiveAny with {1} entity in db")
    @MethodSource({"blockedNotAdminEntities", "deletedNotAdminEntities", "notActiveAdminEntities"})
    void findActiveAnyWithIncorrectEntityInDb_ExceptionExpected(final AccountEntity accountEntity, final String description) {
        accountRepository.save(accountEntity);
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountFinderService.findActiveAny(accountEntity.getId()));
    }

    @ParameterizedTest(name = "findActiveAny with {1} entity in db")
    @MethodSource({"activeNotAdminEntities", "activeAdminEntity", "activeNotAdminEntities"})
    void findActiveAnyWithCorrectEntityInDb_NotNullExpected(final AccountEntity accountEntity, final String description) {
        accountRepository.save(accountEntity);
        Assertions.assertNotNull(accountFinderService.findActiveAny(accountEntity.getId()));
    }
}
