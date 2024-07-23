package me.inquis1tor.userservice.tests.controllers;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.inquis1tor.userservice.dtos.PersonalInfoDto;
import me.inquis1tor.userservice.providers.EndpointsUrls;
import me.inquis1tor.userservice.providers.DtoProvider;
import me.inquis1tor.userservice.services.PersonalInfoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class PersonalInfoControllerTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new
            PostgreSQLContainer<>("postgres:latest");

    @MockBean
    private PersonalInfoService personalInfoService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DtoProvider<PersonalInfoDto> dtoProvider;

    private final static ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Tests PUT /api/personal-infos without auth")
    void updatePersonalInfoWithoutAuth_401Expected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(EndpointsUrls.PERSONAL_INFOS.getPath()))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithJwt("jwt/user.json")
    @DisplayName("Tests PUT /api/personal-infos without body")
    void updatePersonalInfoWithoutBody_409Expected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(EndpointsUrls.PERSONAL_INFOS.getPath())).
                andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @WithJwt("jwt/user.json")
    @DisplayName("Tests PUT /api/personal-infos correctly")
    void updatePersonalInfo_200Expected() throws Exception {
        Mockito.doNothing().when(personalInfoService).updatePersonalInfo(ArgumentMatchers.any(PersonalInfoDto.class));
        mockMvc.perform(MockMvcRequestBuilders.put(EndpointsUrls.PERSONAL_INFOS.getPath()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(dtoProvider.correctDto()))).
                andExpect(MockMvcResultMatchers.status().isOk());
    }
}
