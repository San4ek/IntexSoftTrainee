package org.example.controllers;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.dtos.AddressRequest;
import org.example.entities.AddressEntity;
import org.example.mappers.AddressMapper;
import org.example.repositories.AddressRepository;
import org.example.services.impl.AddressServiceImpl;
import org.example.testutils.AddressRequestBuilder;
import org.example.testutils.EndpointsUrls;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Testcontainers
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AddressControllerTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @MockBean
    private AddressServiceImpl addressService;

    private final MockMvc mockMvc;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final ObjectMapper mapper;
    private AddressEntity addressEntity;
    private AddressRequest addressRequest;

    @BeforeEach
    void setUp() {
        addressRequest = AddressRequestBuilder.anAddressRequest().build();
        addressEntity = addressRepository.save(addressMapper.toEntity(addressRequest));
    }

    @Test
    @WithJwt("resources/jwt/moder.json")
    @DisplayName("Tests GET /api/addresses/{addressId} with valid ID")
    void getAddresses_200Expected() throws Exception {
        when(addressService.getAddresses()).thenReturn(List.of(addressEntity));
        mockMvc.perform(get(EndpointsUrls.ADDRESSES_GET_POST.getPath())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address").value(addressEntity.getAddress()));
    }

    @Test
    @WithJwt("resources/jwt/moder.json")
    @DisplayName("Tests POST /api/addresses with valid body")
    void createAddress_201Expected() throws Exception {
        when(addressService.createAddress(any(AddressRequest.class))).thenReturn(addressEntity);
        mockMvc.perform(post(EndpointsUrls.ADDRESSES_GET_POST.getPath())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(addressRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.address").value(addressEntity.getAddress()));
    }

    @Test
    @WithJwt("resources/jwt/moder.json")
    @DisplayName("Tests PUT /api/addresses/{addressId} with valid body")
    void updateAddress_200Expected() throws Exception {
        final UUID addressId = addressEntity.getId();
        when(addressService.updateAddress(any(UUID.class), any(AddressRequest.class))).thenReturn(addressEntity);
        mockMvc.perform(put(EndpointsUrls.ADDRESSES_PUT_DELETE.getPath() + addressId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(addressRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value(addressEntity.getAddress()));
    }

    @Test
    @WithJwt("resources/jwt/moder.json")
    @DisplayName("Tests DELETE /api/addresses/{addressId} with valid ID")
    void deleteAddress_204Expected() throws Exception {
        final UUID addressId = addressEntity.getId();
        doNothing().when(addressService).deleteAddress(addressId);
        mockMvc.perform(delete(EndpointsUrls.ADDRESSES_PUT_DELETE.getPath() + addressId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithJwt("resources/jwt/moder.json")
    @DisplayName("Tests POST /api/addresses without body")
    void createAddress_400Expected() throws Exception {
        when(addressService.createAddress(any(AddressRequest.class))).thenReturn(addressEntity);
        mockMvc.perform(post(EndpointsUrls.ADDRESSES_GET_POST.getPath())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Tests POST /api/addresses without auth")
    void createAddress_401Expected() throws Exception {
        mockMvc.perform(post(EndpointsUrls.ADDRESSES_GET_POST.getPath()))
                .andExpect(status().isUnauthorized());
    }
}
