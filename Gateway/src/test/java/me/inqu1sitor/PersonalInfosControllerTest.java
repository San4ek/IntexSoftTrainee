package me.inqu1sitor;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.redis.testcontainers.RedisContainer;
import lombok.RequiredArgsConstructor;
import me.inqu1sitor.dtos.PersonalInfoDto;
import me.inqu1sitor.providers.DtoProvider;
import me.inqu1sitor.providers.FinalVariables;
import me.inqu1sitor.providers.ServicesEndpoints;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
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
class PersonalInfosControllerTest {

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
    }

    private final MockMvc mockMvc;
    private final RedisTemplate<String, Object> redisTemplate;
    private final DtoProvider<PersonalInfoDto> dtoProvider;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${" + FinalVariables.USER_SERVICE_HOST_PROPERTY + "}")
    private String userServiceHost;
    @Value("${" + FinalVariables.USER_SERVICE_PORT_PROPERTY + "}")
    private int userServicePort;

    @AfterEach
    void afterEach() {
        redisTemplate.delete(FinalVariables.REDIS_KEY);
    }

    @Test
    @DisplayName("Tests PUT /api/personal-infos without JWT")
    void updatePersonalInfoWithoutAuth_403Expected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(ServicesEndpoints.API_PERSONAL_INFOS)).
                andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @DisplayName("Tests PUT /api/personal-infos without request param")
    void updatePersonalInfoWithoutParam_409Expected() throws Exception {
        redisTemplate.opsForValue().set(FinalVariables.REDIS_KEY, "");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/personal-infos").
                        header(HttpHeaders.AUTHORIZATION, FinalVariables.AUTH_HEADER_VALUE)).
                andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @DisplayName("Tests PUT /api/personal-infos correctly")
    void updatePersonalInfo_200Expected() throws Exception {
        redisTemplate.opsForValue().set(FinalVariables.REDIS_KEY, "");
        final PersonalInfoDto dto = dtoProvider.correctDto();
        final String jsonDto = objectMapper.writeValueAsString(dto);
        wm.stubFor(WireMock.put(WireMock.urlPathEqualTo(ServicesEndpoints.API_PERSONAL_INFOS)).
                withHost(WireMock.equalTo(userServiceHost)).
                withPort(userServicePort).
                withHeader(HttpHeaders.AUTHORIZATION, WireMock.equalTo(FinalVariables.AUTH_HEADER_VALUE)).
                withRequestBody(WireMock.equalTo(jsonDto)).
                willReturn(WireMock.aResponse().withStatus(200)));
        mockMvc.perform(MockMvcRequestBuilders.put(ServicesEndpoints.API_PERSONAL_INFOS).
                        contentType(MediaType.APPLICATION_JSON).
                        content(jsonDto).
                        header(HttpHeaders.AUTHORIZATION, FinalVariables.AUTH_HEADER_VALUE)).
                andExpect(MockMvcResultMatchers.status().isOk());
    }
}
