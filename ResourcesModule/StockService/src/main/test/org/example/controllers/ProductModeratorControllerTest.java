package org.example.controllers;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.dtos.ProductRequest;
import org.example.dtos.ProductResponse;
import org.example.entities.BrandEntity;
import org.example.entities.ProductEntity;
import org.example.mappers.ProductMapper;
import org.example.repositories.BrandRepository;
import org.example.repositories.ProductRepository;
import org.example.services.impl.ProductServiceImpl;
import org.example.testutils.BrandBuilder;
import org.example.testutils.EndpointsUrls;
import org.example.testutils.ProductBuilder;
import org.example.testutils.ProductRequestBuilder;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional
public class ProductModeratorControllerTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @MockBean
    private ProductServiceImpl productService;

    private final MockMvc mockMvc;
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final ProductMapper productMapper;
    private final ObjectMapper mapper;
    private ProductEntity productEntity;
    private ProductRequest productRequest;

    @BeforeEach
    void setUp() {
        final BrandEntity brandEntity = brandRepository.save(BrandBuilder.aBrand().build());
        productRequest = ProductRequestBuilder.aProductRequest().withName("Test Product").withBrandId(brandEntity.getId()).build();
        productEntity = productRepository.save(productMapper.toEntity(productRequest));
    }

    @Test
    @WithJwt("resources/jwt/moder.json")
    @DisplayName("Tests GET /api/products/{id} with valid ID")
    void getProductById_200Expected() throws Exception {
        final UUID productId = productEntity.getId();
        when(productService.getProductById(productId)).thenReturn(productEntity);
        mockMvc.perform(get(EndpointsUrls.PRODUCTS_GET_PUT_DELETE.getPath() + productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId.toString()))
                .andExpect(jsonPath("$.name").value(productEntity.getName()));
    }

    @Test
    @WithJwt("resources/jwt/moder.json")
    @DisplayName("Tests POST /api/products without body")
    void createProductWithoutBody_400Expected() throws Exception {
        mockMvc.perform(post(EndpointsUrls.PRODUCTS_POST.getPath())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithJwt("resources/jwt/moder.json")
    @DisplayName("Tests POST /api/products with valid body")
    void createProduct_201Expected() throws Exception {
        when(productService.createProduct(any(ProductRequest.class))).thenReturn(productEntity);
        mockMvc.perform(post(EndpointsUrls.PRODUCTS_POST.getPath())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(productEntity.getName()));
    }

    @Test
    @DisplayName("Tests POST /api/products without auth")
    void createProductWithoutAuth_401Expected() throws Exception {
        mockMvc.perform(post(EndpointsUrls.PRODUCTS_POST.getPath()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithJwt("resources/jwt/moder.json")
    @DisplayName("Tests PUT /api/products/{id} with valid body")
    void updateProduct_200Expected() throws Exception {
        final UUID productId = productEntity.getId();
        when(productService.updateProduct(eq(productId), any(ProductRequest.class))).thenReturn(productEntity);
        mockMvc.perform(put(EndpointsUrls.PRODUCTS_GET_PUT_DELETE.getPath() + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId.toString()))
                .andExpect(jsonPath("$.name").value(productEntity.getName()));
    }

    @Test
    @WithJwt("resources/jwt/moder.json")
    @DisplayName("Tests DELETE /api/products/{id} with valid ID")
    void deleteProduct_204Expected() throws Exception {
        final UUID productId = productEntity.getId();
        doNothing().when(productService).deleteProduct(productId);
        mockMvc.perform(delete(EndpointsUrls.PRODUCTS_GET_PUT_DELETE.getPath() + productId))
                .andExpect(status().isNoContent());
    }
}
