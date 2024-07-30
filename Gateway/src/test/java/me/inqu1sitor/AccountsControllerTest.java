package me.inqu1sitor;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.http.Body;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.redis.testcontainers.RedisContainer;
import lombok.RequiredArgsConstructor;
import me.inqu1sitor.providers.FinalVariables;
import me.inqu1sitor.providers.ServicesEndpoints;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class AccountsControllerTest {

    @Container
    @ServiceConnection
    private static final RedisContainer redisContainer = new RedisContainer(DockerImageName.parse("redis:latest"));

    @RegisterExtension
    private static final WireMockExtension wm = WireMockExtension.newInstance().
            proxyMode(true).
            options(WireMockConfiguration.wireMockConfig().dynamicPort()).
            build();

    @DynamicPropertySource
    private static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add(FinalVariables.USER_SERVICE_PORT_PROPERTY, wm::getPort);
        registry.add(FinalVariables.AUTH_SERVICE_PORT_PROPERTY, wm::getPort);
    }

    private final MockMvc mockMvc;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${" + FinalVariables.USER_SERVICE_HOST_PROPERTY + "}")
    private String userServiceHost;
    @Value("${" + FinalVariables.USER_SERVICE_PORT_PROPERTY + "}")
    private int userServicePort;
    @Value("${" + FinalVariables.AUTH_SERVICE_HOST_PROPERTY + "}")
    private String authServiceHost;
    @Value("${" + FinalVariables.AUTH_SERVICE_PORT_PROPERTY + "}")
    private int authServicePort;

    @AfterEach
    void afterEach() {
        redisTemplate.delete(FinalVariables.REDIS_KEY);
    }

    @ParameterizedTest(name = "Tests GET /api/accounts without JWT")
    @ValueSource(strings = {ServicesEndpoints.API_ACCOUNTS, ServicesEndpoints.API_ACCOUNTS_ALL})
    void getAccountAndAccountsWithoutAuth_403Expected(final String path) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(path)).
                andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @ParameterizedTest(name = "Tests PUT {0} without JWT")
    @ValueSource(strings = {
            ServicesEndpoints.API_ACCOUNTS_BLOCK,
            ServicesEndpoints.API_ACCOUNTS_UNBLOCK,
            ServicesEndpoints.API_ACCOUNTS_LOGOUT,
            ServicesEndpoints.API_ACCOUNTS_LOGOUT_ALL,
    })
    void blockAndUnblockAccountAndLogoutAndLogoutAllWithoutAuth_403Expected(final String path) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(path)).
                andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @DisplayName("Tests DELETE /api/accounts without JWT")
    void deleteAccountWithoutAuth_403Expected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(ServicesEndpoints.API_ACCOUNTS)).
                andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @ParameterizedTest(name = "Tests GET {0} correctly")
    @ValueSource(strings = {ServicesEndpoints.API_ACCOUNTS, ServicesEndpoints.API_ACCOUNTS_ALL})
    void getAccountAndAllAccounts_200Expected(final String path) throws Exception {
        redisTemplate.opsForValue().set(FinalVariables.REDIS_KEY, "");
        wm.stubFor(WireMock.get(WireMock.urlPathEqualTo(path)).
                withHost(WireMock.equalTo(userServiceHost)).
                withPort(userServicePort).
                withHeader(HttpHeaders.AUTHORIZATION, WireMock.equalTo(FinalVariables.AUTH_HEADER_VALUE)).
                willReturn(WireMock.aResponse().
                        withStatus(200).
                        withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).
                        withResponseBody(new Body(FinalVariables.JSON_RESPONSE_BODY))));
        mockMvc.perform(MockMvcRequestBuilders.get(path).
                        header(HttpHeaders.AUTHORIZATION, FinalVariables.AUTH_HEADER_VALUE)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().json(FinalVariables.JSON_RESPONSE_BODY));
    }

    @ParameterizedTest(name = "Tests PUT {0} without request param")
    @ValueSource(strings = {ServicesEndpoints.API_ACCOUNTS_BLOCK, ServicesEndpoints.API_ACCOUNTS_UNBLOCK})
    void blockAndUnblockAccountWithoutParam_409Expected(final String path) throws Exception {
        redisTemplate.opsForValue().set(FinalVariables.REDIS_KEY, "");
        mockMvc.perform(MockMvcRequestBuilders.put(path).
                        header(HttpHeaders.AUTHORIZATION, FinalVariables.AUTH_HEADER_VALUE)).
                andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @ParameterizedTest(name = "Tests PUT {0} correctly")
    @ValueSource(strings = {ServicesEndpoints.API_ACCOUNTS_BLOCK, ServicesEndpoints.API_ACCOUNTS_UNBLOCK})
    void blockAndUnblockAccount_200Expected(final String path) throws Exception {
        redisTemplate.opsForValue().set(FinalVariables.REDIS_KEY, "");
        wm.stubFor(WireMock.put(WireMock.urlPathEqualTo(path)).
                withHost(WireMock.equalTo(authServiceHost)).
                withPort(authServicePort).
                withQueryParam(FinalVariables.ACCOUNT_ID_PARAM, WireMock.equalTo(FinalVariables.ACCOUNT_ID_PARAM_VALUE)).
                withHeader(HttpHeaders.AUTHORIZATION, WireMock.equalTo(FinalVariables.AUTH_HEADER_VALUE)).
                willReturn(WireMock.aResponse().withStatus(200)));
        mockMvc.perform(MockMvcRequestBuilders.put(path).
                        param(FinalVariables.ACCOUNT_ID_PARAM, FinalVariables.ACCOUNT_ID_PARAM_VALUE).
                        header(HttpHeaders.AUTHORIZATION, FinalVariables.AUTH_HEADER_VALUE)).
                andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Tests DELETE /api/accounts correctly")
    void deleteAccount_200Expected() throws Exception {
        redisTemplate.opsForValue().set(FinalVariables.REDIS_KEY, "");
        System.out.println(authServiceHost + ":" + authServicePort);
        wm.stubFor(WireMock.delete(WireMock.urlPathEqualTo(ServicesEndpoints.API_ACCOUNTS)).
                withHost(WireMock.equalTo(authServiceHost)).
                withPort(authServicePort).
                withHeader(HttpHeaders.AUTHORIZATION, WireMock.equalTo(FinalVariables.AUTH_HEADER_VALUE)).
                willReturn(WireMock.aResponse().withStatus(200)));
        mockMvc.perform(MockMvcRequestBuilders.delete(ServicesEndpoints.API_ACCOUNTS).
                        header(HttpHeaders.AUTHORIZATION, FinalVariables.AUTH_HEADER_VALUE)).
                andExpect(MockMvcResultMatchers.status().isOk());
    }

    @ParameterizedTest(name = "Tests PUT {0} correctly")
    @ValueSource(strings = {ServicesEndpoints.API_ACCOUNTS_LOGOUT, ServicesEndpoints.API_ACCOUNTS_LOGOUT_ALL})
    void logoutAndLogoutAll_200Expected(final String path) throws Exception {
        redisTemplate.opsForValue().set(FinalVariables.REDIS_KEY, "");
        wm.stubFor(WireMock.put(WireMock.urlPathEqualTo(path)).
                withHost(WireMock.equalTo(authServiceHost)).
                withPort(authServicePort).
                withHeader(HttpHeaders.AUTHORIZATION, WireMock.equalTo(FinalVariables.AUTH_HEADER_VALUE)).
                willReturn(WireMock.aResponse().withStatus(200)));
        mockMvc.perform(MockMvcRequestBuilders.put(path).
                        header(HttpHeaders.AUTHORIZATION, FinalVariables.AUTH_HEADER_VALUE)).
                andExpect(MockMvcResultMatchers.status().isOk());
    }
}
