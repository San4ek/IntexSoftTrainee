package me.inqu1sitor.authservice.tests.services;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import jakarta.validation.ConstraintViolationException;
import me.inqu1sitor.authservice.dtos.CredentialsRequestDto;
import me.inqu1sitor.authservice.entities.AccountEntity;
import me.inqu1sitor.authservice.entities.AccountRole;
import me.inqu1sitor.authservice.entities.AccountStatus;
import me.inqu1sitor.authservice.exceptions.AccountNotFoundException;
import me.inqu1sitor.authservice.providers.DtoProvider;
import me.inqu1sitor.authservice.providers.FinalVariables;
import me.inqu1sitor.authservice.providers.ServicesEndpoints;
import me.inqu1sitor.authservice.repositories.AccountRepository;
import me.inqu1sitor.authservice.services.AccountService;
import me.inqu1sitor.authservice.services.LogoutService;
import me.inqu1sitor.authservice.utils.LoggedAccountDetailsProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Testcontainers
class AccountServiceTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @RegisterExtension
    static WireMockExtension wm = WireMockExtension.newInstance().
            proxyMode(true).
            options(WireMockConfiguration.wireMockConfig().dynamicPort()).
            build();

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add(FinalVariables.USER_SERVICE_PORT_PROPERTY, wm::getPort);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DtoProvider<CredentialsRequestDto> dtoProvider;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;
    @MockBean
    private LoggedAccountDetailsProvider loggedAccountDetailsProvider;
    @MockBean
    private LogoutService logoutService;

    @Value("${"+FinalVariables.USER_SERVICE_HOST_PROPERTY+"}")
    private String userServiceHost;
    @Value("${"+FinalVariables.USER_SERVICE_PORT_PROPERTY+"}")
    private int userServicePort;

    @AfterEach
    void afterEach() {
        accountRepository.deleteAll();
    }

    @ParameterizedTest(name = "createAccount with {0} role correctly")
    @EnumSource(AccountRole.class)
    void createAccountWithoutEntityInDb_EqualsExpected(final AccountRole role) {
        wm.stubFor(WireMock.post(WireMock.urlPathEqualTo(ServicesEndpoints.API_ACCOUNTS)).
                withHost(WireMock.equalTo(userServiceHost)).
                withPort(userServicePort).
                willReturn(WireMock.aResponse().withStatus(200)));
        final CredentialsRequestDto dto = dtoProvider.correctDto();
        accountService.createAccount(dto, role);
        final Optional<AccountEntity> optionalAccountEntity = accountRepository.findByEmail(dto.email());
        Assertions.assertTrue(optionalAccountEntity.isPresent());
        Assertions.assertEquals(dto.email(), optionalAccountEntity.get().getEmail());
        Assertions.assertTrue(passwordEncoder.matches(dto.password(), optionalAccountEntity.get().getPassword()));
        Assertions.assertEquals(AccountStatus.ACTIVE, optionalAccountEntity.get().getStatus());
        Assertions.assertEquals(role, optionalAccountEntity.get().getRole());
    }

    @ParameterizedTest(name = "createAccount with already registered email and {0} in db")
    @MethodSource({
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#blockedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#deletedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#notActiveAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeAdminEntity"
    })
    void createAccountWithAlreadyRegisteredEmailInDb_ExceptionExpected(final AccountEntity accountEntity) {
        accountRepository.save(accountEntity);
        final CredentialsRequestDto dto = dtoProvider.correctDto();
        Assertions.assertThrows(ConstraintViolationException.class,
                () -> accountService.createAccount(dto, AccountRole.USER));
    }

    @ParameterizedTest(name = "updateAccount with already registered email and {0} in db")
    @MethodSource({
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#blockedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#deletedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#notActiveAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeAdminEntity"
    })
    void updateAccountWithAlreadyRegisteredEmail_ExceptionExpected(final AccountEntity accountEntity) {
        accountRepository.save(accountEntity);
        final CredentialsRequestDto dto = dtoProvider.correctDto();
        Assertions.assertThrows(ConstraintViolationException.class,
                () -> accountService.updateAccount(dto));
    }

    @Test
    @DisplayName("updateAccount without required entity in db")
    void updateAccountWithoutRequiredEntityInDb_ExceptionExpected() {
        Mockito.doReturn(UUID.randomUUID()).
                when(loggedAccountDetailsProvider).getAccountId();
        final CredentialsRequestDto dto = dtoProvider.correctDto();
        Assertions.assertThrows(AccountNotFoundException.class,
                () -> accountService.updateAccount(dto));
    }

    @ParameterizedTest(name = "updateAccount with incorrect {0} in db")
    @MethodSource({
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#blockedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#deletedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#notActiveAdminEntities"
    })
    void updateAccountWithIncorrectEntityInDb_ExceptionExpected(final AccountEntity accountEntity) {
        Mockito.doReturn(accountEntity.getId()).
                when(loggedAccountDetailsProvider).getAccountId();
        accountEntity.setEmail(FinalVariables.NOT_STANDART_EMAIL);
        accountRepository.save(accountEntity);
        CredentialsRequestDto dto = dtoProvider.correctDto();
        Assertions.assertThrows(AccountNotFoundException.class,
                () -> accountService.updateAccount(dto));
    }

    @ParameterizedTest(name = "updateAccount with correct {0} in db correctly")
    @MethodSource({
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeAdminEntity"
    })
    void updateAccountWithCorrectEntityInDb_EqualsExpected(final AccountEntity accountEntity) {
        wm.stubFor(WireMock.put(WireMock.urlPathEqualTo(ServicesEndpoints.API_ACCOUNTS_CREDENTIALS)).
                withHost(WireMock.equalTo(userServiceHost)).
                withPort(userServicePort).
                willReturn(WireMock.aResponse().withStatus(200)));
        Mockito.doNothing().
                when(logoutService).logoutAll();
        accountEntity.setEmail(FinalVariables.NOT_STANDART_EMAIL);
        accountRepository.save(accountEntity);
        Mockito.doReturn(accountEntity.getId()).
                when(loggedAccountDetailsProvider).getAccountId();
        final CredentialsRequestDto dto = dtoProvider.correctDto();
        accountService.updateAccount(dto);
        final Optional<AccountEntity> optionalAccountEntity = accountRepository.findById(accountEntity.getId());
        Assertions.assertTrue(optionalAccountEntity.isPresent());
        Assertions.assertEquals(dto.email(), optionalAccountEntity.get().getEmail());
        Assertions.assertTrue(passwordEncoder.matches(dto.password(), optionalAccountEntity.get().getPassword()));
        Assertions.assertEquals(accountEntity.getStatus(), optionalAccountEntity.get().getStatus());
        Assertions.assertEquals(accountEntity.getRole(), optionalAccountEntity.get().getRole());
    }

    @Test
    @DisplayName("blockAccount without required entity in db")
    void blockAccountWithoutRequiredEntityInDb_ExceptionExpected() {
        final UUID accountId = UUID.randomUUID();
        Assertions.assertThrows(AccountNotFoundException.class,
                () -> accountService.blockAccount(accountId));
    }

    @ParameterizedTest(name = "blockAccount with incorrect {0} in db")
    @MethodSource({
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#blockedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#deletedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#notActiveAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeAdminEntity"
    })
    void blockAccountWithIncorrectEntityInDb_ExceptionExpected(final AccountEntity accountEntity) {
        accountRepository.save(accountEntity);
        final UUID accountId = accountEntity.getId();
        Assertions.assertThrows(AccountNotFoundException.class,
                () -> accountService.blockAccount(accountId));
    }

    @ParameterizedTest(name = "blockAccount with correct {0} in db correctly")
    @MethodSource("me.inqu1sitor.authservice.providers.ArgumentsProvider#activeNotAdminEntities")
    void blockAccountWithCorrectEntityInDb_EqualsExpected(final AccountEntity accountEntity) {
        Mockito.doReturn(UUID.randomUUID()).
                when(loggedAccountDetailsProvider).getAccountId();
        accountRepository.save(accountEntity);
        Mockito.doNothing().
                when(logoutService).logoutAll(accountEntity.getId());
        wm.stubFor(WireMock.put(WireMock.urlPathEqualTo(ServicesEndpoints.API_ACCOUNTS_BLOCK)).
                withHost(WireMock.equalTo(userServiceHost)).
                withPort(userServicePort).
                willReturn(WireMock.aResponse().withStatus(200)));
        accountService.blockAccount(accountEntity.getId());
        final Optional<AccountEntity> optionalAccountEntity = accountRepository.findById(accountEntity.getId());
        Assertions.assertTrue(optionalAccountEntity.isPresent());
        Assertions.assertEquals(accountEntity.getEmail(), optionalAccountEntity.get().getEmail());
        Assertions.assertEquals(accountEntity.getPassword(), optionalAccountEntity.get().getPassword());
        Assertions.assertEquals(AccountStatus.BLOCKED, optionalAccountEntity.get().getStatus());
        Assertions.assertEquals(accountEntity.getRole(), optionalAccountEntity.get().getRole());
    }

    @Test
    @DisplayName("unblockAccount without required entity in db")
    void unblockAccountWithoutRequiredEntityInDb_ExceptionExpected() {
        final UUID accountId = UUID.randomUUID();
        Assertions.assertThrows(AccountNotFoundException.class,
                () -> accountService.unblockAccount(accountId));
    }

    @ParameterizedTest(name = "unblockAccount with incorrect {0} in db")
    @MethodSource({
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#deletedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#notActiveAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeAdminEntity"
    })
    void unblockAccountWithIncorrectEntityInDb_ExceptionExpected(final AccountEntity accountEntity) {
        accountRepository.save(accountEntity);
        final UUID accountId = accountEntity.getId();
        Assertions.assertThrows(AccountNotFoundException.class,
                () -> accountService.unblockAccount(accountId));
    }

    @ParameterizedTest(name = "unblockAccount with correct {0} in db correctly")
    @MethodSource("me.inqu1sitor.authservice.providers.ArgumentsProvider#blockedNotAdminEntities")
    void unblockAccountWithCorrectEntityInDb_EqualsExpected(final AccountEntity accountEntity) {
        accountRepository.save(accountEntity);
        wm.stubFor(WireMock.put(WireMock.urlPathEqualTo(ServicesEndpoints.API_ACCOUNTS_UNBLOCK)).
                withHost(WireMock.equalTo(userServiceHost)).
                withPort(userServicePort).
                willReturn(WireMock.aResponse().withStatus(200)));
        accountService.unblockAccount(accountEntity.getId());
        final Optional<AccountEntity> optionalAccountEntity = accountRepository.findById(accountEntity.getId());
        Assertions.assertTrue(optionalAccountEntity.isPresent());
        Assertions.assertEquals(accountEntity.getEmail(), optionalAccountEntity.get().getEmail());
        Assertions.assertEquals(accountEntity.getPassword(), optionalAccountEntity.get().getPassword());
        Assertions.assertEquals(AccountStatus.ACTIVE, optionalAccountEntity.get().getStatus());
        Assertions.assertEquals(accountEntity.getRole(), optionalAccountEntity.get().getRole());
    }

    @Test
    void deleteAccountWithoutEntityInDb_ExceptionExpected() {
        Mockito.doReturn(UUID.randomUUID()).when(loggedAccountDetailsProvider).getAccountId();
        Assertions.assertThrows(AccountNotFoundException.class,
                () -> accountService.deleteAccount());
    }

    @ParameterizedTest(name = "deleteAccount with incorrect {0} in db")
    @MethodSource({
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#blockedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#deletedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#notActiveAdminEntities"
    })
    void deleteAccountWithIncorrectEntityInDb_ExceptionExpected(final AccountEntity accountEntity) {
        accountRepository.save(accountEntity);
        Mockito.doReturn(accountEntity.getId()).when(loggedAccountDetailsProvider).getAccountId();
        Assertions.assertThrows(AccountNotFoundException.class,
                () -> accountService.deleteAccount());
    }

    @ParameterizedTest(name = "deleteAccount with correct {0} in db correctly")
    @MethodSource({
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeAdminEntity"
    })
    void deleteAccountWithCorrectEntityInDb_EqualsExpected(final AccountEntity accountEntity) {
        accountRepository.save(accountEntity);
        Mockito.doReturn(accountEntity.getId()).when(loggedAccountDetailsProvider).getAccountId();
        Mockito.doNothing().when(logoutService).logoutAll();
        accountService.deleteAccount();
        final Optional<AccountEntity> optionalAccountEntity = accountRepository.findById(accountEntity.getId());
        Assertions.assertTrue(optionalAccountEntity.isPresent());
        Assertions.assertEquals(accountEntity.getEmail(), optionalAccountEntity.get().getEmail());
        Assertions.assertEquals(accountEntity.getPassword(), optionalAccountEntity.get().getPassword());
        Assertions.assertEquals(AccountStatus.DELETED, optionalAccountEntity.get().getStatus());
        Assertions.assertEquals(accountEntity.getRole(), optionalAccountEntity.get().getRole());
    }
}
