package me.inquis1tor.userservice.tests.services;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class AccountFinderServiceTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    private final AccountFinderService accountFinderService;
    private final AccountRepository accountRepository;
    private static final AccountEntityProvider accountEntityProvider = new AccountEntityProvider();

    @AfterEach
    void afterEach() {
        accountRepository.deleteAll();
    }

    private static Stream<Arguments> activeNotAdminEntities() {
        return Stream.of(
                Arguments.of(accountEntityProvider.activeUserEntity(UUID.randomUUID().toString()), "active user"),
                Arguments.of(accountEntityProvider.activeModerEntity(UUID.randomUUID().toString()), "active moder")
        );
    }

    private static Stream<Arguments> blockedNotAdminEntities() {
        return Stream.of(
                Arguments.of(accountEntityProvider.blockedUserEntity(UUID.randomUUID().toString()), "blocked user"),
                Arguments.of(accountEntityProvider.blockedModerEntity(UUID.randomUUID().toString()), "blocked moder")
        );
    }

    private static Stream<Arguments> deletedNotAdminEntities() {
        return Stream.of(
                Arguments.of(accountEntityProvider.deletedUserEntity(UUID.randomUUID().toString()), "deleted user"),
                Arguments.of(accountEntityProvider.deletedModerEntity(UUID.randomUUID().toString()), "deleted moder")
        );
    }

    private static Stream<Arguments> notActiveAdminEntities() {
        return Stream.of(
                Arguments.of(accountEntityProvider.blockedAdminEntity(UUID.randomUUID().toString()), "blocked admin"),
                Arguments.of(accountEntityProvider.deletedAdminEntity(UUID.randomUUID().toString()), "deleted admin")
        );
    }

    private static Stream<Arguments> activeAdminEntity() {
        return Stream.of(
                Arguments.of(accountEntityProvider.activeAdminEntity(UUID.randomUUID().toString()), "blocked admin")
        );
    }

    @Test
    @DisplayName("findActiveNotAdmin without entity in db")
    void findActiveNotAdminWithoutEntitiesInDb_ExceptionExpected() {
        final UUID accountId = UUID.randomUUID();
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
        final UUID accountId = UUID.randomUUID();
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
        final UUID accountId = UUID.randomUUID();
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountFinderService.findActiveAny(accountId));
    }

    @ParameterizedTest(name = "findActiveAny with {1} entity in db")
    @MethodSource({"blockedNotAdminEntities", "deletedNotAdminEntities", "notActiveAdminEntities"})
    void findActiveAnyWithIncorrectEntityInDb_ExceptionExpected(final AccountEntity accountEntity, final String description) {
        accountRepository.save(accountEntity);
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountFinderService.findActiveAny(accountEntity.getId()));
    }

    @ParameterizedTest(name = "findActiveAny with {1} entity in db")
    @MethodSource({"activeNotAdminEntities", "activeAdminEntity"})
    void findActiveAnyWithCorrectEntityInDb_NotNullExpected(final AccountEntity accountEntity, final String description) {
        accountRepository.save(accountEntity);
        Assertions.assertNotNull(accountFinderService.findActiveAny(accountEntity.getId()));
    }
}
