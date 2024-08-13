package me.inquis1tor.userservice.tests.controllers;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.inquis1tor.userservice.dtos.AccountTransferDto;
import me.inquis1tor.userservice.dtos.CredentialsTransferDto;
import me.inquis1tor.userservice.entities.AccountEntity;
import me.inquis1tor.userservice.entities.AccountStatus;
import me.inquis1tor.userservice.mappers.AccountMapper;
import me.inquis1tor.userservice.providers.AccountEntityProvider;
import me.inquis1tor.userservice.providers.ConstantVariables;
import me.inquis1tor.userservice.providers.DtoProvider;
import me.inquis1tor.userservice.providers.EndpointsUrls;
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
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class AccountControllerTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgres = new
            PostgreSQLContainer<>("postgres:latest");

    private final MockMvc mockMvc;
    private final PrivatePropertiesProvider privatePropertiesProvider;
    private final DtoProvider<AccountTransferDto> accountTransferDtoProvider;
    private final DtoProvider<CredentialsTransferDto> credentialsTransferDtoDtoProvider;
    private final AccountRepository accountRepository;
    private final PersonalInfoRepository personalInfoRepository;
    private final AccountEntityProvider accountEntityProvider;
    private final AccountMapper accountMapper;

    private static final ObjectMapper mapper = new ObjectMapper();

    @AfterEach
    public void AfterEach() {
        personalInfoRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("Tests POST /api/accounts without auth")
    void registerAccountWithoutAuth_401Expected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(EndpointsUrls.ACCOUNTS.getPath())).
                andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("Tests POST /api/accounts without body")
    void registerAccountWithoutBody_400Expected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(EndpointsUrls.ACCOUNTS.getPath()).
                        header(ConstantVariables.AUTH_CODE.getVal(), privatePropertiesProvider.getAuthCode())).
                andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Tests POST /api/accounts with already registered entity in db")
    void registerAccountWithExistedAccountInDb_400Expected() throws Exception {
        final AccountTransferDto accountTransferDto = accountTransferDtoProvider.correctDto();
        AccountEntity userAccount = accountEntityProvider.activeUserEntity(accountTransferDto.id().toString());
        accountRepository.save(userAccount);
        mockMvc.perform(MockMvcRequestBuilders.post(EndpointsUrls.ACCOUNTS.getPath()).
                        header(ConstantVariables.AUTH_CODE.getVal(), privatePropertiesProvider.getAuthCode()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(accountTransferDtoProvider.correctDto()))).
                andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Tests POST /api/accounts with incorrect body")
    void registerAccountWithIncorrectBody_400Expected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(EndpointsUrls.ACCOUNTS.getPath()).
                        header(ConstantVariables.AUTH_CODE.getVal(), privatePropertiesProvider.getAuthCode()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(accountTransferDtoProvider.incorrectDto()))).
                andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Tests POST /api/accounts correctly")
    void registerAccountWithCorrectBody_200Expected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(EndpointsUrls.ACCOUNTS.getPath()).
                        header(ConstantVariables.AUTH_CODE.getVal(), privatePropertiesProvider.getAuthCode()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(accountTransferDtoProvider.correctDto()))).
                andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Tests GET /api/accounts without auth")
    void getAccountWithoutAuth_401Expected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(EndpointsUrls.ACCOUNTS.getPath())).
                andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithJwt("jwt/user.json")
    @DisplayName("Tests GET /api/accounts correctly")
    void getExistedAccountWithCorrectEntityInDb_200Expected() throws Exception {
        AccountEntity account = accountEntityProvider.activeUserEntity(ConstantVariables.UUID.getVal());
        accountRepository.save(account);
        mockMvc.perform(MockMvcRequestBuilders.get(EndpointsUrls.ACCOUNTS.getPath())).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(accountMapper.accountToDto(account))));
    }

    @Test
    @WithJwt("jwt/user.json")
    @DisplayName("Tests GET /api/accounts without entity in db")
    void getAccountWithoutEntityInDb_400Expected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(EndpointsUrls.ACCOUNTS.getPath())).
                andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Tests GET /api/accounts/all without auth")
    void getAllAccountsWithoutAuth_401Expected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(EndpointsUrls.ACCOUNTS_ALL.getPath())).
                andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithJwt("jwt/user.json")
    @DisplayName("Tests GET /api/accounts/all with user role in jwt")
    void getAllAccountsByUser_403Expected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(EndpointsUrls.ACCOUNTS_ALL.getPath())).
                andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Tests GET /api/accounts/all with moder role in jwt")
    void getAllAccountsByModer_403Expected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(EndpointsUrls.ACCOUNTS_ALL.getPath())).
                andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithJwt("jwt/admin.json")
    @DisplayName("Tests GET /api/accounts/all correctly")
    void getAllAccounts_200Expected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(EndpointsUrls.ACCOUNTS_ALL.getPath())).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().json("[]"));
        AccountEntity account = accountEntityProvider.activeUserEntity(UUID.randomUUID().toString());
        accountRepository.save(account);
        mockMvc.perform(MockMvcRequestBuilders.get(EndpointsUrls.ACCOUNTS_ALL.getPath())).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().json("[ " +
                        mapper.writeValueAsString(accountMapper.accountToDto(account)) +
                        " ]"));
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/block without auth")
    void blockAccountWithoutAuth_401Expected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(EndpointsUrls.ACCOUNTS_BLOCK.getPath())).
                andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/block without params")
    void blockAccountWithoutParams_400Expected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(EndpointsUrls.ACCOUNTS_BLOCK.getPath()).
                        header(ConstantVariables.AUTH_CODE.getVal(), privatePropertiesProvider.getAuthCode())).
                andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/block by not existed admin entity")
    void blockAccountWithoutAdminEntityInDb_400Expected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(EndpointsUrls.ACCOUNTS_BLOCK.getPath()).
                        header(ConstantVariables.AUTH_CODE.getVal(), privatePropertiesProvider.getAuthCode()).
                        param(ConstantVariables.ACCOUNT_ID.getVal(), UUID.randomUUID().toString()).
                        param(ConstantVariables.ADMIN_ID.getVal(), UUID.randomUUID().toString())).
                andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/block with not existed blocking entity in db")
    void blockAccountWithoutBlockingEntityInDb_404Expected() throws Exception {
        AccountEntity adminAccount = accountEntityProvider.activeAdminEntity(UUID.randomUUID().toString());
        accountRepository.save(adminAccount);
        mockMvc.perform(MockMvcRequestBuilders.put(EndpointsUrls.ACCOUNTS_BLOCK.getPath()).
                        header(ConstantVariables.AUTH_CODE.getVal(), privatePropertiesProvider.getAuthCode()).
                        param(ConstantVariables.ACCOUNT_ID.getVal(), UUID.randomUUID().toString()).
                        param(ConstantVariables.ADMIN_ID.getVal(), adminAccount.getId().toString())).
                andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/block correctly")
    void blockAccount_200Expected() throws Exception {
        AccountEntity adminAccount = accountEntityProvider.activeAdminEntity(UUID.randomUUID().toString());
        accountRepository.save(adminAccount);
        AccountEntity moderAccount = accountEntityProvider.activeModerEntity(UUID.randomUUID().toString());
        moderAccount.setEmail("test1.@test.ru");
        accountRepository.save(moderAccount);
        mockMvc.perform(MockMvcRequestBuilders.put(EndpointsUrls.ACCOUNTS_BLOCK.getPath()).
                        header(ConstantVariables.AUTH_CODE.getVal(), privatePropertiesProvider.getAuthCode()).
                        param(ConstantVariables.ACCOUNT_ID.getVal(), moderAccount.getId().toString()).
                        param(ConstantVariables.ADMIN_ID.getVal(), adminAccount.getId().toString())).
                andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/block with already blocked entity in db")
    void blockAccountWithAlreadyBlockedEntityInDb_404Expected() throws Exception {
        AccountEntity adminAccount = accountEntityProvider.activeAdminEntity(UUID.randomUUID().toString());
        accountRepository.save(adminAccount);
        AccountEntity moderAccount = accountEntityProvider.activeModerEntity(UUID.randomUUID().toString());
        moderAccount.setStatus(AccountStatus.BLOCKED);
        moderAccount.setEmail("test1@test.ru");
        accountRepository.save(moderAccount);
        mockMvc.perform(MockMvcRequestBuilders.put(EndpointsUrls.ACCOUNTS_BLOCK.getPath()).
                        header(ConstantVariables.AUTH_CODE.getVal(), privatePropertiesProvider.getAuthCode()).
                        param(ConstantVariables.ACCOUNT_ID.getVal(), adminAccount.getId().toString()).
                        param(ConstantVariables.ADMIN_ID.getVal(), adminAccount.getId().toString())).
                andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/block with active admin entity in db")
    void blockAccountWithAdminEntityInDb_404Expected() throws Exception {
        AccountEntity adminAccount = accountEntityProvider.activeAdminEntity(UUID.randomUUID().toString());
        accountRepository.save(adminAccount);
        mockMvc.perform(MockMvcRequestBuilders.put(EndpointsUrls.ACCOUNTS_BLOCK.getPath()).
                        header(ConstantVariables.AUTH_CODE.getVal(), privatePropertiesProvider.getAuthCode()).
                        param(ConstantVariables.ACCOUNT_ID.getVal(), adminAccount.getId().toString()).
                        param(ConstantVariables.ADMIN_ID.getVal(), adminAccount.getId().toString())).
                andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/unblock without auth")
    void unblockAccountWithoutAuth_401Expected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(EndpointsUrls.ACCOUNTS_UNBLOCK.getPath())).
                andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/unblock without params")
    void unblockAccountWithoutParams_400Expected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(EndpointsUrls.ACCOUNTS_UNBLOCK.getPath()).
                        header(ConstantVariables.AUTH_CODE.getVal(), privatePropertiesProvider.getAuthCode())).
                andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/unblock without entity in db")
    void unblockAccountWithoutEntityInDb_404Expected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(EndpointsUrls.ACCOUNTS_UNBLOCK.getPath()).
                        header(ConstantVariables.AUTH_CODE.getVal(), privatePropertiesProvider.getAuthCode()).
                        param(ConstantVariables.ACCOUNT_ID.getVal(), UUID.randomUUID().toString())).
                andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/unblock with active user entity in db")
    void unblockAccountWithIncorrectEntityInDb_404Expected() throws Exception {
        AccountEntity userAccount = accountEntityProvider.activeUserEntity(UUID.randomUUID().toString());
        accountRepository.save(userAccount);
        mockMvc.perform(MockMvcRequestBuilders.put(EndpointsUrls.ACCOUNTS_UNBLOCK.getPath()).
                        header(ConstantVariables.AUTH_CODE.getVal(), privatePropertiesProvider.getAuthCode()).
                        param(ConstantVariables.ACCOUNT_ID.getVal(), userAccount.getId().toString())).
                andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts/unblock correctly")
    void unblockAccount_200Expected() throws Exception {
        AccountEntity userAccount = accountEntityProvider.activeUserEntity(UUID.randomUUID().toString());
        userAccount.setStatus(AccountStatus.BLOCKED);
        accountRepository.save(userAccount);
        mockMvc.perform(MockMvcRequestBuilders.put(EndpointsUrls.ACCOUNTS_UNBLOCK.getPath()).
                        header(ConstantVariables.AUTH_CODE.getVal(), privatePropertiesProvider.getAuthCode()).
                        param(ConstantVariables.ACCOUNT_ID.getVal(), userAccount.getId().toString())).
                andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts without auth")
    void updateCredentialsWithoutAuth_401Expected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(EndpointsUrls.ACCOUNTS.getPath())).
                andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts without body")
    void updateCredentialsWithoutBody_400Expected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(EndpointsUrls.ACCOUNTS.getPath()).
                        header(ConstantVariables.AUTH_CODE.getVal(), privatePropertiesProvider.getAuthCode())).
                andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts with incorrect body")
    void updateCredentialsWithIncorrectBody_400Expected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(EndpointsUrls.ACCOUNTS.getPath()).
                        header(ConstantVariables.AUTH_CODE.getVal(), privatePropertiesProvider.getAuthCode()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(credentialsTransferDtoDtoProvider.incorrectDto()))).
                andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts without entity in db")
    void updateCredentialsWithoutEntityInDb_404Expected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(EndpointsUrls.ACCOUNTS.getPath()).
                        header(ConstantVariables.AUTH_CODE.getVal(), privatePropertiesProvider.getAuthCode()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(credentialsTransferDtoDtoProvider.correctDto()))).
                andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts with already registered email in db")
    void updateCredentialsWithAlreadyRegisteredEmailInDb_400Expected() throws Exception {
        CredentialsTransferDto dto = credentialsTransferDtoDtoProvider.correctDto();
        accountRepository.save(accountEntityProvider.activeUserEntity(String.valueOf(dto.id())));
        mockMvc.perform(MockMvcRequestBuilders.put(EndpointsUrls.ACCOUNTS.getPath()).
                        header(ConstantVariables.AUTH_CODE.getVal(), privatePropertiesProvider.getAuthCode()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(dto))).
                andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts correctly")
    void updateCredentials_200Expected() throws Exception {
        CredentialsTransferDto dto = credentialsTransferDtoDtoProvider.correctDto();
        AccountEntity accountEntity = accountEntityProvider.activeUserEntity(String.valueOf(dto.id()));
        accountEntity.setEmail("test2@test.ru");
        accountRepository.save(accountEntity);
        mockMvc.perform(MockMvcRequestBuilders.put(EndpointsUrls.ACCOUNTS.getPath()).
                        header(ConstantVariables.AUTH_CODE.getVal(), privatePropertiesProvider.getAuthCode()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(dto))).
                andExpect(MockMvcResultMatchers.status().isOk());
    }
}
