package me.inqu1sitor.authservice.tests.controllers;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import lombok.RequiredArgsConstructor;
import me.inqu1sitor.authservice.dtos.CredentialsRequestDto;
import me.inqu1sitor.authservice.dtos.SendMailRequestDto;
import me.inqu1sitor.authservice.entities.AccountEntity;
import me.inqu1sitor.authservice.entities.AccountRole;
import me.inqu1sitor.authservice.entities.AccountStatus;
import me.inqu1sitor.authservice.providers.DtoProvider;
import me.inqu1sitor.authservice.providers.FinalVariables;
import me.inqu1sitor.authservice.providers.ServicesEndpoints;
import me.inqu1sitor.authservice.rabbit.Notifier;
import me.inqu1sitor.authservice.repositories.AccountRepository;
import me.inqu1sitor.authservice.services.LogoutService;
import me.inqu1sitor.authservice.utils.LoggedAccountDetailsProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
import java.util.UUID;

/**
 * The {@code @SpyBean} doesn't work at all, that's why some tests,
 * uses {@link #loggedAccountDetailsProvider}, don't work.
 * <p>
 * Not working tests:
 * <ul>
 *     <li>{@link #deleteAccountWithCorrectEntityInDbAndUserAuth_200Expected}</li>
 *     <li>{@link #updateAccountWithCorrectEntityInDb_200Expected}</li>
 *     <li>{@link #updateAccountWithIncorrectEntityInDb_400Expected}</li>
 * </ul>
 */
@SpringBootTest(properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration,org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration")
@Testcontainers
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class AccountControllerTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @RegisterExtension
    private static final WireMockExtension wm = WireMockExtension.newInstance().
            proxyMode(true).
            options(WireMockConfiguration.wireMockConfig().dynamicPort()).
            build();

    @DynamicPropertySource
    private static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add(FinalVariables.USER_SERVICE_PORT_PROPERTY, wm::getPort);
    }

    private final MockMvc mockMvc;
    private final DtoProvider<CredentialsRequestDto> dtoProvider;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @SpyBean
    private LoggedAccountDetailsProvider loggedAccountDetailsProvider;
    @MockBean
    private LogoutService logoutService;
    @MockBean
    private Notifier<SendMailRequestDto> sendMailNotifier;
    @MockBean
    private Notifier<UUID> accountDeletedNotifier;
    @MockBean
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${" + FinalVariables.USER_SERVICE_HOST_PROPERTY + "}")
    private String userServiceHost;
    @Value("${" + FinalVariables.USER_SERVICE_PORT_PROPERTY + "}")
    private int userServicePort;

    private static final ObjectMapper mapper = new ObjectMapper();

    @AfterEach
    void afterEach() {
        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("Tests POST /api/accounts/user correctly")
    void registerUser_200Expected() throws Exception {
        wm.stubFor(WireMock.post(WireMock.urlPathEqualTo(ServicesEndpoints.API_ACCOUNTS)).
                withHost(WireMock.equalTo(userServiceHost)).
                withPort(userServicePort).
                willReturn(WireMock.aResponse().withStatus(200)));
        final CredentialsRequestDto dto = dtoProvider.correctDto();
        mockMvc.perform(MockMvcRequestBuilders.post(ServicesEndpoints.API_ACCOUNTS_USER).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(dto))).
                andExpect(MockMvcResultMatchers.status().isOk());
        final Optional<AccountEntity> optionalAccountEntity = accountRepository.findByEmail(dto.email());
        Assertions.assertTrue(optionalAccountEntity.isPresent());
        Assertions.assertEquals(dto.email(), optionalAccountEntity.get().getEmail());
        Assertions.assertTrue(passwordEncoder.matches(dto.password(), optionalAccountEntity.get().getPassword()));
        Assertions.assertEquals(AccountStatus.ACTIVE, optionalAccountEntity.get().getStatus());
        Assertions.assertEquals(AccountRole.USER, optionalAccountEntity.get().getRole());
    }

    @ParameterizedTest(name = "Tests POST {0} correctly")
    @WithJwt("jwt/admin.json")
    @ValueSource(strings = {ServicesEndpoints.API_ACCOUNTS_MODER, ServicesEndpoints.API_ACCOUNTS_ADMIN})
    void registerModerAndAdmin_200Expected(final String path) throws Exception {
        Mockito.doReturn(true).when(redisTemplate).hasKey(Mockito.anyString());
        wm.stubFor(WireMock.post(WireMock.urlPathEqualTo(ServicesEndpoints.API_ACCOUNTS)).
                withHost(WireMock.equalTo(userServiceHost)).
                withPort(userServicePort).
                willReturn(WireMock.aResponse().withStatus(200)));
        final CredentialsRequestDto dto = dtoProvider.correctDto();
        mockMvc.perform(MockMvcRequestBuilders.post(path).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(dto)).
                        header(HttpHeaders.AUTHORIZATION, "")).
                andExpect(MockMvcResultMatchers.status().isOk());
        final Optional<AccountEntity> optionalAccountEntity = accountRepository.findByEmail(dto.email());
        Assertions.assertTrue(optionalAccountEntity.isPresent());
        Assertions.assertEquals(dto.email(), optionalAccountEntity.get().getEmail());
        Assertions.assertTrue(passwordEncoder.matches(dto.password(), optionalAccountEntity.get().getPassword()));
        Assertions.assertEquals(AccountStatus.ACTIVE, optionalAccountEntity.get().getStatus());
        switch (path) {
            case ServicesEndpoints.API_ACCOUNTS_MODER ->
                    Assertions.assertEquals(AccountRole.MODER, optionalAccountEntity.get().getRole());
            case ServicesEndpoints.API_ACCOUNTS_ADMIN ->
                    Assertions.assertEquals(AccountRole.ADMIN, optionalAccountEntity.get().getRole());
        }
    }

    @ParameterizedTest(name = "Tests POST {0} without JWT")
    @ValueSource(strings = {ServicesEndpoints.API_ACCOUNTS_MODER, ServicesEndpoints.API_ACCOUNTS_ADMIN})
    void registerModerAndAdminWithoutAuth_401Expected(final String path) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(path)).
                andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @ParameterizedTest(name = "Tests POST {0} with \"moder\" JWT")
    @WithJwt("jwt/moder.json")
    @ValueSource(strings = {ServicesEndpoints.API_ACCOUNTS_MODER, ServicesEndpoints.API_ACCOUNTS_ADMIN})
    void registerModerAndAdminWithModerAuth_403Expected(final String path) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(path)).
                andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @ParameterizedTest(name = "Tests POST {0} with \"user\" JWT")
    @WithJwt("jwt/user.json")
    @ValueSource(strings = {ServicesEndpoints.API_ACCOUNTS_MODER, ServicesEndpoints.API_ACCOUNTS_ADMIN})
    void registerModerAndAdminWithUserAuth_403Expected(final String path) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(path)).
                andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @DisplayName("Tests POST /api/accounts/user without request body")
    void registerUserWithoutBody_400Expected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(ServicesEndpoints.API_ACCOUNTS_USER)).
                andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @ParameterizedTest(name = "Tests POST {0} without request body")
    @WithJwt("jwt/admin.json")
    @ValueSource(strings = {ServicesEndpoints.API_ACCOUNTS_MODER, ServicesEndpoints.API_ACCOUNTS_ADMIN})
    void registerModerAndAdminWithoutBody_400Expected(final String path) throws Exception {
        Mockito.doReturn(true).when(redisTemplate).hasKey(Mockito.anyString());
        mockMvc.perform(MockMvcRequestBuilders.post(path).
                        header(HttpHeaders.AUTHORIZATION, "")).
                andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Tests POST /api/accounts/user with incorrect request body")
    void registerUserWithIncorrectBody_400Expected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(ServicesEndpoints.API_ACCOUNTS_USER).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(dtoProvider.incorrectDto()))).
                andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @ParameterizedTest(name = "Tests POST {0} with incorrect request body")
    @WithJwt("jwt/admin.json")
    @ValueSource(strings = {ServicesEndpoints.API_ACCOUNTS_MODER, ServicesEndpoints.API_ACCOUNTS_ADMIN})
    void registerModerAndAdminWithIncorrectBody_400Expected(final String path) throws Exception {
        Mockito.doReturn(true).when(redisTemplate).hasKey(Mockito.anyString());
        mockMvc.perform(MockMvcRequestBuilders.post(path).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(dtoProvider.incorrectDto())).
                        header(HttpHeaders.AUTHORIZATION, "")).
                andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @ParameterizedTest(name = "Tests POST /api/accounts/user with already registered email and {0} in db")
    @MethodSource({
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#blockedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#deletedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#notActiveAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeAdminEntity"
    })
    void registerUserWithAlreadyRegisteredEmail_400Expected(final AccountEntity accountEntity) throws Exception {
        accountRepository.save(accountEntity);
        mockMvc.perform(MockMvcRequestBuilders.post(ServicesEndpoints.API_ACCOUNTS_USER).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(dtoProvider.correctDto()))).
                andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @ParameterizedTest(name = "Tests POST /api/accounts/moder with already registered email and {0} in db")
    @WithJwt("jwt/admin.json")
    @MethodSource({
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#blockedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#deletedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#notActiveAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeAdminEntity"
    })
    void registerModerWithAlreadyRegisteredEmail_400Expected(final AccountEntity accountEntity) throws Exception {
        Mockito.doReturn(true).when(redisTemplate).hasKey(Mockito.anyString());
        accountRepository.save(accountEntity);
        mockMvc.perform(MockMvcRequestBuilders.post(ServicesEndpoints.API_ACCOUNTS_MODER).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(dtoProvider.correctDto())).
                        header(HttpHeaders.AUTHORIZATION, "")).
                andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @ParameterizedTest(name = "Tests POST /api/accounts/admin with already registered email and {0} in db")
    @WithJwt("jwt/admin.json")
    @MethodSource({
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#blockedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#deletedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#notActiveAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeAdminEntity"
    })
    void registerAdminWithAlreadyRegisteredEmail_400Expected(final AccountEntity accountEntity) throws Exception {
        Mockito.doReturn(true).when(redisTemplate).hasKey(Mockito.anyString());
        accountRepository.save(accountEntity);
        mockMvc.perform(MockMvcRequestBuilders.post(ServicesEndpoints.API_ACCOUNTS_ADMIN).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(dtoProvider.correctDto())).
                        header(HttpHeaders.AUTHORIZATION, "")).
                andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @ParameterizedTest(name = "Tests POST {0} without JWT")
    @ValueSource(strings = {ServicesEndpoints.API_ACCOUNTS_BLOCK, ServicesEndpoints.API_ACCOUNTS_UNBLOCK})
    void blockAndUnblockAccountWithoutAuth_401Expected(final String path) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(path)).
                andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @ParameterizedTest(name = "Tests POST {0} with \"user\" JWT")
    @WithJwt("jwt/user.json")
    @ValueSource(strings = {ServicesEndpoints.API_ACCOUNTS_BLOCK, ServicesEndpoints.API_ACCOUNTS_UNBLOCK})
    void blockAndUnblockAccountWithUserAuth_403Expected(final String path) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(path)).
                andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @ParameterizedTest(name = "Tests POST {0} with \"moder\" JWT")
    @WithJwt("jwt/moder.json")
    @ValueSource(strings = {ServicesEndpoints.API_ACCOUNTS_BLOCK, ServicesEndpoints.API_ACCOUNTS_UNBLOCK})
    void blockAndUnblockAccountWithModerAuth_403Expected(final String path) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(path)).
                andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @ParameterizedTest(name = "Tests POST {0} without request param")
    @WithJwt("jwt/admin.json")
    @ValueSource(strings = {ServicesEndpoints.API_ACCOUNTS_BLOCK, ServicesEndpoints.API_ACCOUNTS_UNBLOCK})
    void blockAndUnblockAccountWithoutParam_400Expected(final String path) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(path)).
                andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @ParameterizedTest(name = "Tests POST {0} without required entity in db")
    @WithJwt("jwt/admin.json")
    @ValueSource(strings = {ServicesEndpoints.API_ACCOUNTS_BLOCK, ServicesEndpoints.API_ACCOUNTS_UNBLOCK})
    void blockAndUnblockAccountWithoutRequiredEntityInDb_404Expected(final String path) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(path).
                        param(FinalVariables.ACCOUNT_ID_PARAM, UUID.randomUUID().toString())).
                andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @ParameterizedTest(name = "Tests POST /api/accounts/block with incorrect {0} in db")
    @WithJwt("jwt/admin.json")
    @MethodSource({
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#blockedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#deletedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#notActiveAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeAdminEntity"
    })
    void blockAccountWithIncorrectEntityInDb_404Expected(final AccountEntity accountEntity) throws Exception {
        accountRepository.save(accountEntity);
        mockMvc.perform(MockMvcRequestBuilders.put(ServicesEndpoints.API_ACCOUNTS_BLOCK).
                        param(FinalVariables.ACCOUNT_ID_PARAM, accountEntity.getId().toString())).
                andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @ParameterizedTest(name = "Tests POST /api/accounts/unblock with incorrect {0} in db")
    @WithJwt("jwt/admin.json")
    @MethodSource({
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#deletedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#notActiveAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeAdminEntity"
    })
    void unblockAccountWithIncorrectEntityInDb_404Expected(final AccountEntity accountEntity) throws Exception {
        accountRepository.save(accountEntity);
        mockMvc.perform(MockMvcRequestBuilders.put(ServicesEndpoints.API_ACCOUNTS_UNBLOCK).
                        param(FinalVariables.ACCOUNT_ID_PARAM, accountEntity.getId().toString())).
                andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @ParameterizedTest(name = "Tests POST /api/accounts/block with correct {0} in db correctly")
    @WithJwt("jwt/admin.json")
    @MethodSource("me.inqu1sitor.authservice.providers.ArgumentsProvider#activeNotAdminEntities")
    void blockAccountWithCorrectEntityInDb_200Expected(final AccountEntity accountEntity) throws Exception {
        accountRepository.save(accountEntity);
        wm.stubFor(WireMock.put(WireMock.urlPathEqualTo(ServicesEndpoints.API_ACCOUNTS_BLOCK)).
                withHost(WireMock.equalTo(userServiceHost)).
                withPort(userServicePort).
                willReturn(WireMock.aResponse().withStatus(200)));
        mockMvc.perform(MockMvcRequestBuilders.put(ServicesEndpoints.API_ACCOUNTS_BLOCK).
                        param(FinalVariables.ACCOUNT_ID_PARAM, accountEntity.getId().toString())).
                andExpect(MockMvcResultMatchers.status().isOk());
        Optional<AccountEntity> optionalAccountEntity = accountRepository.findById(accountEntity.getId());
        Assertions.assertTrue(optionalAccountEntity.isPresent());
        Assertions.assertEquals(accountEntity.getEmail(), optionalAccountEntity.get().getEmail());
        Assertions.assertEquals(accountEntity.getPassword(), optionalAccountEntity.get().getPassword());
        Assertions.assertEquals(accountEntity.getRole(), optionalAccountEntity.get().getRole());
        Assertions.assertEquals(AccountStatus.BLOCKED, optionalAccountEntity.get().getStatus());
    }

    @ParameterizedTest(name = "Tests POST /api/accounts/unblock with correct {0} in db correctly")
    @WithJwt("jwt/admin.json")
    @MethodSource("me.inqu1sitor.authservice.providers.ArgumentsProvider#blockedNotAdminEntities")
    void unblockAccountWithCorrectEntityInDb_200Expected(final AccountEntity accountEntity) throws Exception {
        accountRepository.save(accountEntity);
        wm.stubFor(WireMock.put(WireMock.urlPathEqualTo(ServicesEndpoints.API_ACCOUNTS_UNBLOCK)).
                withHost(WireMock.equalTo(userServiceHost)).
                withPort(userServicePort).
                willReturn(WireMock.aResponse().withStatus(200)));
        mockMvc.perform(MockMvcRequestBuilders.put(ServicesEndpoints.API_ACCOUNTS_UNBLOCK).
                        param(FinalVariables.ACCOUNT_ID_PARAM, accountEntity.getId().toString())).
                andExpect(MockMvcResultMatchers.status().isOk());
        Optional<AccountEntity> optionalAccountEntity = accountRepository.findById(accountEntity.getId());
        Assertions.assertTrue(optionalAccountEntity.isPresent());
        Assertions.assertEquals(accountEntity.getEmail(), optionalAccountEntity.get().getEmail());
        Assertions.assertEquals(accountEntity.getPassword(), optionalAccountEntity.get().getPassword());
        Assertions.assertEquals(accountEntity.getRole(), optionalAccountEntity.get().getRole());
        Assertions.assertEquals(AccountStatus.ACTIVE, optionalAccountEntity.get().getStatus());
    }

    @Test
    @DisplayName("Tests DELETE /api/accounts without JWT")
    void deleteAccountWithoutAuth_401Expected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(ServicesEndpoints.API_ACCOUNTS)).
                andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithJwt("jwt/user.json")
    @DisplayName("Tests DELETE /api/accounts without required entity in db")
    void deleteAccountWithoutRequiredEntityInDb_404Expected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(ServicesEndpoints.API_ACCOUNTS)).
                andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @ParameterizedTest(name = "Tests DELETE /api/accounts with incorrect {0} in db and \"user\" JWT")
    @WithJwt("jwt/user.json")
    @MethodSource({
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#blockedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#deletedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#notActiveAdminEntities",
    })
    void deleteAccountWithIncorrectEntityInDbAndUserAuth_404Expected(final AccountEntity accountEntity) throws Exception {
        accountRepository.save(accountEntity);
        mockMvc.perform(MockMvcRequestBuilders.delete(ServicesEndpoints.API_ACCOUNTS)).
                andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @ParameterizedTest(name = "Tests DELETE /api/accounts with correct {0} in db correctly")
    @WithJwt("jwt/user.json")
    @MethodSource({
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeAdminEntity"
    })
    void deleteAccountWithCorrectEntityInDbAndUserAuth_200Expected(final AccountEntity accountEntity) throws Exception {
        accountRepository.save(accountEntity);
        Mockito.doReturn(accountEntity.getId()).when(loggedAccountDetailsProvider).getAccountId();
        mockMvc.perform(MockMvcRequestBuilders.delete(ServicesEndpoints.API_ACCOUNTS)).
                andExpect(MockMvcResultMatchers.status().isOk());
        final Optional<AccountEntity> optionalAccountEntity = accountRepository.findById(accountEntity.getId());
        Assertions.assertTrue(optionalAccountEntity.isPresent());
        Assertions.assertEquals(accountEntity.getEmail(), optionalAccountEntity.get().getEmail());
        Assertions.assertEquals(accountEntity.getPassword(), optionalAccountEntity.get().getPassword());
        Assertions.assertEquals(AccountStatus.BLOCKED, optionalAccountEntity.get().getStatus());
        Assertions.assertEquals(accountEntity.getRole(), optionalAccountEntity.get().getRole());
    }

    @Test
    @DisplayName("Tests PUT /api/accounts without JWT")
    void updateAccountWithoutAuth_401Expected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(ServicesEndpoints.API_ACCOUNTS)).
                andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithJwt("jwt/user.json")
    @DisplayName("Tests PUT /api/accounts without request body")
    void updateAccountWithoutBody_400Expected() throws Exception {
        Mockito.doReturn(true).when(redisTemplate).hasKey(Mockito.anyString());
        mockMvc.perform(MockMvcRequestBuilders.put(ServicesEndpoints.API_ACCOUNTS).
                        header(HttpHeaders.AUTHORIZATION, "")).
                andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithJwt("jwt/admin.json")
    @DisplayName("Tests PUT /api/accounts without required entity in db")
    void updateAccountWithoutRequiredEntityInDb_404Expected() throws Exception {
        Mockito.doReturn(true).when(redisTemplate).hasKey(Mockito.anyString());
        mockMvc.perform(MockMvcRequestBuilders.put(ServicesEndpoints.API_ACCOUNTS).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(dtoProvider.correctDto())).
                        header(HttpHeaders.AUTHORIZATION, "")).
                andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithJwt("jwt/admin.json")
    @DisplayName("Tests PUT /api/accounts with incorrect request body")
    void updateAccountWithIncorrectBody_400Expected() throws Exception {
        Mockito.doReturn(true).when(redisTemplate).hasKey(Mockito.anyString());
        mockMvc.perform(MockMvcRequestBuilders.put(ServicesEndpoints.API_ACCOUNTS).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(dtoProvider.incorrectDto())).
                        header(HttpHeaders.AUTHORIZATION, "")).
                andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @ParameterizedTest(name = "Tests PUT /api/accounts with {0} in db and already registered email")
    @WithJwt("jwt/user.json")
    @MethodSource({
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#blockedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#deletedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#notActiveAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeAdminEntity"
    })
    void updateAccountWithAlreadyRegisteredEmail_400Expected(final AccountEntity accountEntity) throws Exception {
        Mockito.doReturn(true).when(redisTemplate).hasKey(Mockito.anyString());
        accountRepository.save(accountEntity);
        mockMvc.perform(MockMvcRequestBuilders.put(ServicesEndpoints.API_ACCOUNTS).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(dtoProvider.correctDto())).
                        header(HttpHeaders.AUTHORIZATION, "")).
                andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @ParameterizedTest(name = "Tests PUT /api/accounts with incorrect {0} in db and \"admin\" JWT")
    @DirtiesContext
    @WithJwt("jwt/admin.json")
    @MethodSource({
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#blockedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#deletedNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#notActiveAdminEntities"
    })
    void updateAccountWithIncorrectEntityInDb_400Expected(final AccountEntity accountEntity) throws Exception {
        accountRepository.save(accountEntity);
        final UUID accountId = accountEntity.getId();
        Mockito.doReturn(accountId).when(loggedAccountDetailsProvider).getAccountId();
        mockMvc.perform(MockMvcRequestBuilders.put(ServicesEndpoints.API_ACCOUNTS).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(dtoProvider.correctDto()))).
                andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @ParameterizedTest(name = "Tests PUT /api/accounts with correct {0} in db correctly")
    @WithJwt("jwt/admin.json")
    @MethodSource({
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeNotAdminEntities",
            "me.inqu1sitor.authservice.providers.ArgumentsProvider#activeAdminEntity"
    })
    void updateAccountWithCorrectEntityInDb_200Expected(final AccountEntity accountEntity) throws Exception {
        wm.stubFor(WireMock.put(WireMock.urlPathEqualTo(ServicesEndpoints.API_ACCOUNTS)).
                withHost(WireMock.equalTo(userServiceHost)).
                withPort(userServicePort).
                willReturn(WireMock.aResponse().withStatus(200)));
        accountEntity.setEmail(FinalVariables.NOT_STANDART_EMAIL);
        accountRepository.save(accountEntity);
        final UUID accountId = accountEntity.getId();
        Mockito.doReturn(accountId).when(loggedAccountDetailsProvider).getAccountId();
        final CredentialsRequestDto dto = dtoProvider.correctDto();
        mockMvc.perform(MockMvcRequestBuilders.put(ServicesEndpoints.API_ACCOUNTS).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(dto))).
                andExpect(MockMvcResultMatchers.status().isOk());
        final Optional<AccountEntity> optionalAccountEntity = accountRepository.findById(accountId);
        Assertions.assertTrue(optionalAccountEntity.isPresent());
        Assertions.assertEquals(dto.email(), optionalAccountEntity.get().getEmail());
        Assertions.assertTrue(passwordEncoder.matches(dto.password(), optionalAccountEntity.get().getPassword()));
        Assertions.assertEquals(accountEntity.getStatus(), optionalAccountEntity.get().getStatus());
        Assertions.assertEquals(accountEntity.getRole(), optionalAccountEntity.get().getRole());
    }
}
