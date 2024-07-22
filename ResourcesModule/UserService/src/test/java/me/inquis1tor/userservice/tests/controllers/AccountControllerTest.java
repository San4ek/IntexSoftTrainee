package me.inquis1tor.userservice.tests.controllers;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.inquis1tor.userservice.dtos.AccountTransferDto;
import me.inquis1tor.userservice.dtos.CredentialsTransferDto;
import me.inquis1tor.userservice.entities.AccountEntity;
import me.inquis1tor.userservice.mappers.AccountMapper;
import me.inquis1tor.userservice.providers.AccountEntityProvider;
import me.inquis1tor.userservice.providers.DtoProvider;
import me.inquis1tor.userservice.repositories.AccountRepository;
import me.inquis1tor.userservice.repositories.PersonalInfoRepository;
import me.inquis1tor.userservice.utils.PrivatePropertiesProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class AccountControllerTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new
            PostgreSQLContainer<>("postgres:latest");

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PrivatePropertiesProvider privatePropertiesProvider;
    @Autowired
    private DtoProvider<AccountTransferDto> accountTransferDtoProvider;
    @Autowired
    private DtoProvider<CredentialsTransferDto> credentialsTransferDtoDtoProvider;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PersonalInfoRepository personalInfoRepository;
    @Autowired
    private AccountEntityProvider accountEntityProvider;
    @Autowired
    private AccountMapper accountMapper;

    private static final ObjectMapper mapper = new ObjectMapper();

    @AfterEach
    public void AfterEach() {
        personalInfoRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("Tests POST /api/accounts, 401 expected")
    void registerAccountWithoutAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts")).
                andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("Tests POST /api/accounts, 409 expected")
    void registerAccountWithoutBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts").
                        header("Auth-Code", privatePropertiesProvider.getAuthCode())).
                andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @DisplayName("Tests POST /api/accounts, 400 expected")
    void registerExistedAccount() throws Exception {
        AccountEntity userAccount = accountEntityProvider.activeUserEntity("c0a80065-90a2-1cb0-8190-a20de91f0000");
        accountRepository.save(userAccount);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts").
                        header("Auth-Code", privatePropertiesProvider.getAuthCode()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(accountTransferDtoProvider.correctDto()))).
                andExpect(MockMvcResultMatchers.status().isBadRequest());

        userAccount.setId(UUID.fromString("c0a80065-90a2-1cb0-8190-a20de91f0000"));
        userAccount.setEmail("test1@mail.ru");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts").
                        header("Auth-Code", privatePropertiesProvider.getAuthCode()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(accountTransferDtoProvider.correctDto()))).
                andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Tests POST /api/accounts, 417 expected")
    void registerAccountWithIncorrectBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts").
                        header("Auth-Code", privatePropertiesProvider.getAuthCode()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(accountTransferDtoProvider.incorrectDto()))).
                andExpect(MockMvcResultMatchers.status().isExpectationFailed());
    }

    @Test
    @DisplayName("Tests POST /api/accounts, 200 expected")
    void registerAccountWithCorrectBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts").
                        header("Auth-Code", privatePropertiesProvider.getAuthCode()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(accountTransferDtoProvider.correctDto()))).
                andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Tests GET /api/accounts, 401 expected")
    void getAccountWithoutAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts")).
                andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithJwt("jwt/user.json")
    @DisplayName("Tests GET /api/accounts, 200 expected")
    void getExistedAccount() throws Exception {
        AccountEntity account = accountEntityProvider.activeUserEntity("c0a80065-90a2-1cb0-8190-a20de91f0000");
        accountRepository.save(account);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(accountMapper.accountToDto(account))));
    }

    @Test
    @WithJwt("jwt/user.json")
    @DisplayName("Tests GET /api/accounts, 404 expected")
    void getNotExistedAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts")).
                andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Tests GET /api/accounts/all, 401 expected")
    void getAllAccountsWithoutAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/all")).
                andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithJwt("jwt/user.json")
    @DisplayName("Tests GET /api/accounts/all, 403 expected")
    void getAllAccountsByUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/all")).
                andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Tests GET /api/accounts/all, 403 expected")
    void getAllAccountsByModer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/all")).
                andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithJwt("jwt/admin.json")
    @DisplayName("Tests GET /api/accounts/all, 200 expected")
    void getAllAccounts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/all")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().json("[]"));

        AccountEntity account = accountEntityProvider.activeUserEntity("c0a80065-90a2-1cb0-8190-a20de91f0001");
        accountRepository.save(account);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/all")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().json("[ " +
                        mapper.writeValueAsString(accountMapper.accountToDto(account)) +
                        " ]"));
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/block, 401 expected")
    void blockAccountWithoutAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/accounts/block")).
                andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/block, 417 expected")
    void blockAccountWithoutParams() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/accounts/block").
                        header("Auth-Code", privatePropertiesProvider.getAuthCode())).
                andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/block, 400 expected")
    void blockAccountWithNotExistedAdminId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/accounts/block").
                        header("Auth-Code", privatePropertiesProvider.getAuthCode()).
                        param("accountId", "c0a80065-90a2-1cb0-8190-a20de91f0001").
                        param("adminId", "c0a80065-90a2-1cb0-8190-a20de91f0000")).
                andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/block, 404 expected")
    void blockNotExistedAccount() throws Exception {
        AccountEntity adminAccount = accountEntityProvider.activeAdminEntity("c0a80065-90a2-1cb0-8190-a20de91f0000");
        accountRepository.save(adminAccount);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/accounts/block").
                        header("Auth-Code", privatePropertiesProvider.getAuthCode()).
                        param("accountId", "c0a80065-90a2-1cb0-8190-a20de91f0001").
                        param("adminId", "c0a80065-90a2-1cb0-8190-a20de91f0000")).
                andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/block, 200 expected")
    void blockAccount() throws Exception {
        AccountEntity adminAccount = accountEntityProvider.activeAdminEntity("c0a80065-90a2-1cb0-8190-a20de91f0000");
        accountRepository.save(adminAccount);
        AccountEntity moderAccount = accountEntityProvider.activeModerEntity("c0a80065-90a2-1cb0-8190-a20de91f0001");
        moderAccount.setEmail("test1.@test.ru");
        accountRepository.save(moderAccount);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/accounts/block").
                        header("Auth-Code", privatePropertiesProvider.getAuthCode()).
                        param("accountId", "c0a80065-90a2-1cb0-8190-a20de91f0001").
                        param("adminId", "c0a80065-90a2-1cb0-8190-a20de91f0000")).
                andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/block, 404 expected")
    void blockAlreadyBlockedAccount() throws Exception {
        AccountEntity adminAccount = accountEntityProvider.activeAdminEntity("c0a80065-90a2-1cb0-8190-a20de91f0000");
        accountRepository.save(adminAccount);
        AccountEntity moderAccount = accountEntityProvider.activeModerEntity("c0a80065-90a2-1cb0-8190-a20de91f0001");
        moderAccount.setStatus(AccountEntity.Status.BLOCKED);
        moderAccount.setEmail("test1@test.ru");
        accountRepository.save(moderAccount);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/accounts/block").
                        header("Auth-Code", privatePropertiesProvider.getAuthCode()).
                        param("accountId", "c0a80065-90a2-1cb0-8190-a20de91f0001").
                        param("adminId", "c0a80065-90a2-1cb0-8190-a20de91f0000")).
                andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/block, 404 expected")
    void blockAdminAccount() throws Exception {
        AccountEntity adminAccount = accountEntityProvider.activeAdminEntity("c0a80065-90a2-1cb0-8190-a20de91f0000");
        accountRepository.save(adminAccount);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/accounts/block").
                        header("Auth-Code", privatePropertiesProvider.getAuthCode()).
                        param("accountId", "c0a80065-90a2-1cb0-8190-a20de91f0000").
                        param("adminId", "c0a80065-90a2-1cb0-8190-a20de91f0000")).
                andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/unblock, 401 expected")
    void unblockAccountWithoutAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/accounts/unblock")).
                andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/unblock, 417 expected")
    void unblockAccountWithoutParams() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/accounts/unblock").
                        header("Auth-Code", privatePropertiesProvider.getAuthCode())).
                andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/unblock, 404 expected")
    void unblockNotExistedAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/accounts/unblock").
                        header("Auth-Code", privatePropertiesProvider.getAuthCode()).
                        param("accountId", "c0a80065-90a2-1cb0-8190-a20de91f0000")).
                andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/unblock, 404 expected")
    void unblockNotBLockedAccount() throws Exception {
        AccountEntity userAccount = accountEntityProvider.activeUserEntity("c0a80065-90a2-1cb0-8190-a20de91f0000");
        accountRepository.save(userAccount);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/accounts/unblock").
                        header("Auth-Code", privatePropertiesProvider.getAuthCode()).
                        param("accountId", "c0a80065-90a2-1cb0-8190-a20de91f0000")).
                andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/unblock, 404 expected")
    void unblockAdminAccount() throws Exception {
        AccountEntity adminAccount = accountEntityProvider.activeAdminEntity("c0a80065-90a2-1cb0-8190-a20de91f0000");
        accountRepository.save(adminAccount);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/accounts/unblock").
                        header("Auth-Code", privatePropertiesProvider.getAuthCode()).
                        param("accountId", "c0a80065-90a2-1cb0-8190-a20de91f0000")).
                andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/unblock, 200 expected")
    void unblockAccount() throws Exception {
        AccountEntity userAccount = accountEntityProvider.activeUserEntity("c0a80065-90a2-1cb0-8190-a20de91f0000");
        userAccount.setStatus(AccountEntity.Status.BLOCKED);
        accountRepository.save(userAccount);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/accounts/unblock").
                        header("Auth-Code", privatePropertiesProvider.getAuthCode()).
                        param("accountId", "c0a80065-90a2-1cb0-8190-a20de91f0000")).
                andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/credentials, 401 expected")
    void updateCredentialsWithoutAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/accounts/credentials")).
                andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/credentials, 409 expected")
    void updateCredentialsWithoutBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/accounts/credentials").
                        header("Auth-Code", privatePropertiesProvider.getAuthCode())).
                andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/credentials, 417 expected")
    void updateCredentialsWithIncorrectBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/accounts/credentials").
                        header("Auth-Code", privatePropertiesProvider.getAuthCode()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(credentialsTransferDtoDtoProvider.incorrectDto()))).
                andExpect(MockMvcResultMatchers.status().isExpectationFailed());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/credentials, 404 expected")
    void updateNotExistedAccountCredentials() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/accounts/credentials").
                        header("Auth-Code", privatePropertiesProvider.getAuthCode()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(credentialsTransferDtoDtoProvider.correctDto()))).
                andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/credentials, 400 expected")
    void updateWithAlreadyRegisteredCredentials() throws Exception {
        CredentialsTransferDto dto = credentialsTransferDtoDtoProvider.correctDto();
        accountRepository.save(accountEntityProvider.activeUserEntity(String.valueOf(dto.id())));
        mockMvc.perform(MockMvcRequestBuilders.put("/api/accounts/credentials").
                        header("Auth-Code", privatePropertiesProvider.getAuthCode()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(dto))).
                andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/credentials, 200 expected")
    void updateCredentials() throws Exception {
        CredentialsTransferDto dto = credentialsTransferDtoDtoProvider.correctDto();
        AccountEntity accountEntity = accountEntityProvider.activeUserEntity(String.valueOf(dto.id()));
        accountEntity.setEmail("test2@test.ru");
        accountRepository.save(accountEntity);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/accounts/credentials").
                        header("Auth-Code", privatePropertiesProvider.getAuthCode()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(dto))).
                andExpect(MockMvcResultMatchers.status().isOk());
    }
}
