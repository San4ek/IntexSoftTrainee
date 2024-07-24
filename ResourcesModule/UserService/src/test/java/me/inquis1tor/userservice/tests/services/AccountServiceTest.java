package me.inquis1tor.userservice.tests.services;


import jakarta.validation.ConstraintViolationException;
import me.inquis1tor.userservice.dtos.AccountResponseDto;
import me.inquis1tor.userservice.dtos.AccountTransferDto;
import me.inquis1tor.userservice.dtos.CredentialsTransferDto;
import me.inquis1tor.userservice.dtos.PersonalInfoDto;
import me.inquis1tor.userservice.entities.AccountEntity;
import me.inquis1tor.userservice.entities.PersonalInfoEntity;
import me.inquis1tor.userservice.exceptions.AccountNotFoundException;
import me.inquis1tor.userservice.mappers.AccountMapper;
import me.inquis1tor.userservice.providers.AccountEntityProvider;
import me.inquis1tor.userservice.providers.DtoProvider;
import me.inquis1tor.userservice.repositories.AccountRepository;
import me.inquis1tor.userservice.services.AccountService;
import me.inquis1tor.userservice.utils.LoggedAccountDetailsProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@Testcontainers
class AccountServiceTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private DtoProvider<AccountTransferDto> accountTransferDtoDtoProvider;
    @Autowired
    private DtoProvider<CredentialsTransferDto> credentialsTransferDtoProvider;
    private static final AccountEntityProvider accountEntityProvider = new AccountEntityProvider();
    @Autowired
    private AccountMapper accountMapper;
    @MockBean
    private LoggedAccountDetailsProvider holder;

    @AfterEach
    void afterEach() {
        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("createAccount with already registered email in db")
    void createAccountWithAlreadyRegisteredEmailInDb_ExceptionExpected() {
        final AccountEntity accountEntity = accountEntityProvider.blockedUserEntity(UUID.randomUUID().toString());
        final AccountTransferDto accountTransferDto = accountTransferDtoDtoProvider.correctDto();
        accountRepository.save(accountEntity);
        Assertions.assertThrows(ConstraintViolationException.class, () -> accountService.createAccount(accountTransferDto));
    }

    @Test
    @DisplayName("createAccount with empty db")
    void createAccountWithEmptyDb_EqualsExpected() {
        final AccountTransferDto accountTransferDto = accountTransferDtoDtoProvider.correctDto();
        accountService.createAccount(accountTransferDto);
        final AccountEntity accountEntity = accountRepository.findByEmail(accountTransferDto.email());
        Assertions.assertNotNull(accountEntity);
        Assertions.assertEquals(accountTransferDto.id(), accountEntity.getId());
        Assertions.assertEquals(accountTransferDto.email(), accountEntity.getEmail());
        Assertions.assertEquals(accountTransferDto.role(), accountEntity.getRole());
        Assertions.assertEquals(AccountEntity.Status.ACTIVE, accountEntity.getStatus());
        final PersonalInfoEntity personalInfoEntity = accountEntity.getPersonalInfo();
        Assertions.assertNotNull(personalInfoEntity);
        Assertions.assertEquals(accountTransferDto.id(), personalInfoEntity.getId());
        Assertions.assertNull(personalInfoEntity.getName());
        Assertions.assertNull(personalInfoEntity.getSurname());
        Assertions.assertNull(personalInfoEntity.getPatronymic());
        Assertions.assertNull(personalInfoEntity.getPhoneNumber());
    }

    @Test
    @DisplayName("getAccount without entity in db")
    void getAccountWithIncorrectEntityInDb_EqualsExpected() {
        Mockito.doReturn(UUID.randomUUID()).when(holder).getAccountId();
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.getAccount());
    }

    @Test
    @DisplayName("getAccount with active user entity in db")
    void getAccountWithCorrectEntityInDb_EqualsExpected() {
        final UUID accountId = UUID.randomUUID();
        final AccountEntity accountEntity = accountEntityProvider.activeUserEntity(accountId.toString());
        accountRepository.save(accountEntity);
        Mockito.doReturn(accountEntity.getId()).when(holder).getAccountId();
        final AccountResponseDto accountResponseDto = accountService.getAccount();
        Assertions.assertNotNull(accountResponseDto);
        Assertions.assertEquals(accountEntity.getId(), accountResponseDto.id());
        Assertions.assertEquals(accountEntity.getEmail(), accountResponseDto.email());
        Assertions.assertEquals(accountEntity.getRole(), accountResponseDto.role());
        Assertions.assertEquals(accountEntity.getStatus(), accountResponseDto.status());
        final PersonalInfoDto personalInfoDto = accountResponseDto.personalInfo();
        Assertions.assertNotNull(personalInfoDto);
    }

    @Test
    @DisplayName("getAllAccounts with entities in db")
    void getAllWithEntitiesInDb_IterableEqualsExpected() {
        final AccountEntity accountEntity = accountEntityProvider.activeUserEntity(UUID.randomUUID().toString());
        accountRepository.save(accountEntity);
        Assertions.assertIterableEquals(accountService.getAll(), accountMapper.accountListToDtoList(List.of(accountEntity)));
    }

    @Test
    @DisplayName("deleteAccount without entity in db")
    void deleteAccountWithIncorrectEntityInDb_ExceptionExpected() {
        final UUID accountId = UUID.randomUUID();
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.deleteAccount(accountId));
    }

    @Test
    @DisplayName("deleteAccount with active user entity in db")
    void deleteAccountWithCorrectEntityInDb_EqualsExpected() {
        final AccountEntity accountEntity = accountEntityProvider.activeUserEntity(UUID.randomUUID().toString());
        accountRepository.save(accountEntity);
        accountService.deleteAccount(accountEntity.getId());
        Assertions.assertEquals(AccountEntity.Status.DELETED, accountRepository.findByEmail(accountEntity.getEmail()).getStatus());
    }

    @Test
    @DisplayName("blockAccount without blocking entity and active admin entity in db")
    void blockAccountWithIncorrectBlockingEntityAndCorrectAdminInDb_ExceptionExpected() {
        final AccountEntity adminEntity = accountEntityProvider.activeAdminEntity(UUID.randomUUID().toString());
        accountRepository.save(adminEntity);
        final UUID adminId = adminEntity.getId();
        final UUID userId = UUID.randomUUID();
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.blockAccount(userId, adminId));
    }

    @Test
    @DisplayName("blockAccount with blocked user entity and without admin entity in db")
    void blockAccountWithCorrectBlockingEntityAndIncorrectAdminInDb_ExceptionExpected() {
        final AccountEntity userEntity = accountEntityProvider.blockedUserEntity(UUID.randomUUID().toString());
        accountRepository.save(userEntity);
        final UUID adminId = UUID.randomUUID();
        final UUID userId = userEntity.getId();
        Assertions.assertThrows(ConstraintViolationException.class, () -> accountService.blockAccount(userId, adminId));
    }

    @Test
    @DisplayName("blockAccount with active user and active admin entities in db")
    void blockAccountWithCorrectBlockingEntityAndCorrectAdminInDb_ExceptionExpected() {
        final AccountEntity adminEntity = accountEntityProvider.activeAdminEntity(UUID.randomUUID().toString());
        adminEntity.setEmail("test1@test.ru");
        final AccountEntity userEntity = accountEntityProvider.activeUserEntity(UUID.randomUUID().toString());
        accountRepository.saveAll(List.of(adminEntity, userEntity));
        final UUID adminId = adminEntity.getId();
        final UUID userId = userEntity.getId();
        accountService.blockAccount(userId, adminId);
        Assertions.assertEquals(AccountEntity.Status.BLOCKED, accountRepository.findByEmail(userEntity.getEmail()).getStatus());
    }

    @Test
    @DisplayName("unblockAccount without entity in db")
    void unblockAccountWithIncorrectEntityInDb_ExceptionExpected() {
        final UUID accountId = UUID.randomUUID();
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.unblockAccount(accountId));
    }

    @Test
    @DisplayName("unblockAccount with blocked user entity in db")
    void unblockAccountWithCorrectEntityInDb_ExceptionExpected() {
        final AccountEntity userEntity = accountEntityProvider.blockedUserEntity(UUID.randomUUID().toString());
        accountRepository.save(userEntity);
        Assertions.assertEquals(AccountEntity.Status.BLOCKED, accountRepository.findByEmail(userEntity.getEmail()).getStatus());
    }

    @Test
    @DisplayName("updateCredentials without entity in db")
    void updateCredentialsWithIncorrectEntityInDb_ExceptionExpected() {
        final CredentialsTransferDto dto = credentialsTransferDtoProvider.correctDto();
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.updateCredentials(dto));
    }

    @Test
    @DisplayName("updateCredentials with active user entity in db")
    void updateCredentialsWithCorrectEntityInDb_EqualsExpected() {
        final CredentialsTransferDto credentialsTransferDto = credentialsTransferDtoProvider.correctDto();
        final AccountEntity accountEntity = accountEntityProvider.activeUserEntity(credentialsTransferDto.id().toString());
        accountEntity.setEmail("test1@test.ru");
        accountRepository.save(accountEntity);
        accountService.updateCredentials(credentialsTransferDto);
        Assertions.assertNotNull(accountRepository.findByEmail(credentialsTransferDto.email()));
    }

    @Test
    @DisplayName("updateCredentials with already registered email in db")
    void updateCredentialsWithAlreadyRegisteredEmailInDb_EqualsExpected() {
        final AccountEntity accountEntity = accountEntityProvider.activeUserEntity(UUID.randomUUID().toString());
        accountRepository.save(accountEntity);
        final CredentialsTransferDto credentialsTransferDto = credentialsTransferDtoProvider.correctDto();
        Assertions.assertThrows(ConstraintViolationException.class, () -> accountService.updateCredentials(credentialsTransferDto));
    }
}
