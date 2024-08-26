package org.example.controllers;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.dtos.BrandRequest;
import org.example.entities.BrandEntity;
import org.example.mappers.BrandMapper;
import org.example.repositories.BrandRepository;
import org.example.services.impl.BrandServiceImpl;
import org.example.testutils.BrandRequestBuilder;
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

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BrandModeratorControllerTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @MockBean
    private BrandServiceImpl brandService;

    private final MockMvc mockMvc;
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    private final ObjectMapper mapper;
    private BrandEntity brandEntity;
    private BrandRequest brandRequest;

    @BeforeEach
    void setUp() {
        brandRequest = BrandRequestBuilder.aBrandRequest().build();
        brandEntity = brandRepository.save(brandMapper.toEntity(brandRequest));
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Tests GET /api/brands/{id} with valid ID")
    void getBrandById_200Expected() throws Exception {
        final UUID brandId = brandEntity.getId();
        when(brandService.getBrandById(brandId)).thenReturn(brandEntity);
        mockMvc.perform(get(EndpointsUrls.BRANDS_GET_PUT_DELETE.getPath() + brandId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(brandId.toString()))
                .andExpect(jsonPath("$.name").value(brandEntity.getName()));
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Tests POST /api/brands without body")
    void createBrandWithoutBody_400Expected() throws Exception {
        mockMvc.perform(post(EndpointsUrls.BRANDS_POST.getPath())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Tests POST /api/brands with valid body")
    void createBrand_201Expected() throws Exception {
        when(brandService.createBrand(any(BrandRequest.class))).thenReturn(brandEntity);
        mockMvc.perform(post(EndpointsUrls.BRANDS_POST.getPath())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(brandRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(brandEntity.getName()));
    }


    @Test
    @DisplayName("Tests POST /api/brands without auth")
    void createBrandWithoutAuth_401Expected() throws Exception {
        mockMvc.perform(post(EndpointsUrls.BRANDS_POST.getPath()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithJwt("jwt/user.json")
    @DisplayName("Tests POST /api/brands with user auth")
    void createBrandWithUserAuth_403Expected() throws Exception {
        mockMvc.perform(post(EndpointsUrls.BRANDS_POST.getPath()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Tests PUT /api/brands/{id} with valid body")
    void updateBrand_200Expected() throws Exception {
        final UUID brandId = brandEntity.getId();
        when(brandService.updateBrand(eq(brandId), any(BrandRequest.class))).thenReturn(brandEntity);
        mockMvc.perform(put(EndpointsUrls.BRANDS_GET_PUT_DELETE.getPath() + brandId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(brandRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(brandId.toString()))
                .andExpect(jsonPath("$.name").value(brandEntity.getName()));
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Tests DELETE /api/brands/{id} with valid ID")
    void deleteBrand_204Expected() throws Exception {
        final UUID brandId = brandEntity.getId();
        doNothing().when(brandService).deleteBrand(brandId);
        mockMvc.perform(delete(EndpointsUrls.BRANDS_GET_PUT_DELETE.getPath() + brandId))
                .andExpect(status().isNoContent());
    }
}
