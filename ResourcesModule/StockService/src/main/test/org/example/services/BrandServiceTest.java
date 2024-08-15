package org.example.services;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.example.dtos.BrandRequest;
import org.example.entities.BrandEntity;
import org.example.entities.ProductEntity;
import org.example.exceptions.InvalidObjectException;
import org.example.exceptions.ObjectNotFoundException;
import org.example.repositories.ProductRepository;
import org.example.services.impl.BrandServiceImpl;
import org.example.testutils.BrandBuilder;
import org.example.testutils.BrandRequestBuilder;
import org.example.repositories.BrandRepository;
import org.example.testutils.ProductBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Testcontainers
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BrandServiceTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    private final BrandServiceImpl brandService;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    @Test
    @DisplayName("Should find brand by id")
    void shouldGetBrandById() {
        final BrandEntity savedBrand = brandRepository.save(BrandBuilder.aBrand().build());
        final BrandEntity foundBrand = brandService.getBrandById(savedBrand.getId());
        assertEquals(savedBrand, foundBrand);
    }

    @Test
    @DisplayName("Should throw exception when find not existing brand")
    void shouldThrowExceptionWhenFindNotExistingBrand() {
        assertThrows(ObjectNotFoundException.class, () -> brandService.getBrandById(UUID.randomUUID()));
    }

    @Test
    @DisplayName("Should create brand")
    void shouldCreateBrand() {
        final BrandRequest brandRequest = BrandRequestBuilder.aBrandRequest().build();
        final BrandEntity savedBrand = brandService.createBrand(brandRequest);
        assertNotNull(savedBrand);
        assertEquals(brandRequest.getName(), savedBrand.getName());
    }

    @Test
    @DisplayName("Should throw exception when brand name not set for creation")
    void shouldThrowExceptionWhenBrandNameNotSet() {
        final BrandRequest brandRequest = BrandRequestBuilder.aBrandRequest().withName(null).build();
        assertThrows(ConstraintViolationException.class, () -> brandService.createBrand(brandRequest));
    }

    @Test
    @DisplayName("Should throw exception when brand is already exists")
    void shouldThrowExceptionWhenBrandIsAlreadyExist() {
        final BrandRequest brandRequest = BrandRequestBuilder.aBrandRequest().build();
        final BrandEntity firstSavedBrand = brandService.createBrand(brandRequest);
        assertThrows(InvalidObjectException.class, () -> brandService.createBrand(brandRequest));
    }

    @Test
    @DisplayName("Should update brand")
    void shouldUpdateBrand() {
        final BrandEntity savedBrand = brandRepository.save(BrandBuilder.aBrand().withName("Marko").build());
        final BrandRequest brandRequest = BrandRequestBuilder.aBrandRequest().withName("Goochi").build();
        final BrandEntity updatedBrand = brandService.updateBrand(savedBrand.getId(), brandRequest);
        assertEquals(brandRequest.getName(), updatedBrand.getName());
    }

    @Test
    @DisplayName("Should throw exception when brand for update not exists")
    void shouldThrowExceptionWhenBrandForUpdateNotExist() {
        final BrandRequest brandRequest = BrandRequestBuilder.aBrandRequest().build();
        assertThrows(InvalidObjectException.class, () -> brandService.updateBrand(UUID.randomUUID(), brandRequest));
    }

    @Test
    @DisplayName("Should throw exception when brand name not set for update")
    void shouldThrowExceptionWhenBrandNameForUpdateNotSet() {
        final BrandRequest brandRequest = BrandRequestBuilder.aBrandRequest().withName(null).build();
        final BrandEntity savedBrand = brandRepository.save(BrandBuilder.aBrand().build());
        assertThrows(ConstraintViolationException.class, () -> brandService.updateBrand(savedBrand.getId(), brandRequest));
    }

    @Test
    @DisplayName("Should throw exception when update brand with existing name")
    void shouldThrowExceptionWhenNewBrandNameIsAlreadyExist() {
        final BrandRequest brandRequest = BrandRequestBuilder.aBrandRequest().build();
        final BrandEntity savedBrand = brandService.createBrand(brandRequest);
        assertThrows(InvalidObjectException.class, () -> brandService.updateBrand(savedBrand.getId(), brandRequest));
    }

    @Test
    @DisplayName("Should delete brand")
    void shouldDeleteBrand() {
        final BrandEntity savedBrand = brandRepository.save(BrandBuilder.aBrand().build());
        brandService.deleteBrand(savedBrand.getId());
        assertThrows(ObjectNotFoundException.class, () -> brandService.getBrandById(savedBrand.getId()));
    }

    @Test
    @DisplayName("Should throw exception when delete not existing brand")
    void shouldThrowExceptionWhenDeleteNotExistingBrand() {
        assertThrows(InvalidObjectException.class, () -> brandService.deleteBrand(UUID.randomUUID()));
    }

    @Test
    @DisplayName("Should throw exception when product with brand exists")
    void shouldThrowExceptionWhenProductWithBrandExists() {
        final BrandEntity savedBrand = brandRepository.save(BrandBuilder.aBrand().build());
        final ProductEntity savedProduct = productRepository.save(ProductBuilder.aProduct().withBrand(savedBrand).build());
        assertThrows(InvalidObjectException.class, () -> brandService.deleteBrand(savedBrand.getId()));
    }
}
