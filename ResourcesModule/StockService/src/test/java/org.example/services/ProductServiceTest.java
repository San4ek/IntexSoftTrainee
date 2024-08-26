package org.example.services;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.example.dtos.ProductRequest;
import org.example.entities.BrandEntity;
import org.example.entities.ProductEntity;
import org.example.entities.StockEntity;
import org.example.enums.CurrencyEnum;
import org.example.enums.TypeEnum;
import org.example.exceptions.InvalidObjectException;
import org.example.exceptions.ObjectNotFoundException;
import org.example.repositories.BrandRepository;
import org.example.repositories.ProductRepository;
import org.example.repositories.StockRepository;
import org.example.services.impl.ProductServiceImpl;
import org.example.testutils.BrandBuilder;
import org.example.testutils.ProductBuilder;
import org.example.testutils.ProductRequestBuilder;
import org.example.testutils.StockItemBuilder;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@Testcontainers
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductServiceTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    private final ProductServiceImpl productService;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final BrandRepository brandRepository;
    private BrandEntity brand;

    @BeforeEach
    void setUp() {
        brand = brandRepository.save(BrandBuilder.aBrand().build());
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Should find product by id")
    void shouldGetProductById() {
        final ProductEntity savedProduct = productRepository.save(ProductBuilder.aProduct().withBrand(brand).build());
        final ProductEntity foundProduct = productService.getProductById(savedProduct.getId());
        assertEquals(savedProduct, foundProduct);
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Should throw exception when find not existing product")
    void shouldThrowExceptionWhenFindNotExistingProduct() {
        assertThrows(ObjectNotFoundException.class, () -> productService.getProductById(UUID.randomUUID()));
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Should create product")
    void shouldCreateProduct() {
        final ProductRequest productRequest = ProductRequestBuilder.aProductRequest().withBrandId(brand.getId()).build();
        final ProductEntity savedProduct = productService.createProduct(productRequest);
        assertEquals(productRequest.getName(), savedProduct.getName());
        assertEquals(productRequest.getType(), savedProduct.getType());
        assertEquals(productRequest.getPrice(), savedProduct.getPrice());
        assertEquals(productRequest.getCurrency(), savedProduct.getCurrency());
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Should throw exception when product name not set for creation")
    void shouldThrowExceptionWhenProductNameNotSet() {
        final ProductRequest productRequest = ProductRequestBuilder.aProductRequest().withName(null).build();
        assertThrows(ConstraintViolationException.class, () -> productService.createProduct(productRequest));
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Should throw exception when product is already exists")
    void shouldThrowExceptionWhenProductIsAlreadyExist() {
        final ProductRequest productRequest = ProductRequestBuilder.aProductRequest().withBrandId(brand.getId()).build();
        final ProductEntity firstSavedProduct = productService.createProduct(productRequest);
        assertThrows(InvalidObjectException.class, () -> productService.createProduct(productRequest));
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Should update product")
    void shouldUpdateProduct() {
        final ProductRequest createProductRequest = ProductRequestBuilder.aProductRequest()
                .withName("Tapochki")
                .withType(TypeEnum.SNEAKERS)
                .withCurrency(CurrencyEnum.USD)
                .withPrice(10.14D)
                .withBrandId(brand.getId())
                .build();
        final ProductEntity savedProduct = productService.createProduct(createProductRequest);
        final ProductRequest updateProductRequest = ProductRequestBuilder.aProductRequest()
                .withName("Tapki")
                .withType(TypeEnum.SHOES)
                .withCurrency(CurrencyEnum.EUR)
                .withPrice(23.99D)
                .withBrandId(brand.getId())
                .build();
        final ProductEntity updatedProduct = productService.updateProduct(savedProduct.getId(), updateProductRequest);
        assertEquals(updateProductRequest.getName(), updatedProduct.getName());
        assertEquals(updateProductRequest.getType(), updatedProduct.getType());
        assertEquals(updateProductRequest.getCurrency(), updatedProduct.getCurrency());
        assertEquals(updateProductRequest.getPrice(), updatedProduct.getPrice());
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Should throw exception when product for update not exists")
    void shouldThrowExceptionWhenProductForUpdateNotExist() {
        final ProductRequest productRequest = ProductRequestBuilder.aProductRequest().build();
        assertThrows(InvalidObjectException.class, () -> productService.updateProduct(UUID.randomUUID(), productRequest));
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Should throw exception when product name not set for update")
    void shouldThrowExceptionWhenProductNameForUpdateNotSet() {
        final ProductRequest productRequest = ProductRequestBuilder.aProductRequest().withName(null).build();
        final ProductEntity savedProduct = productRepository.save(ProductBuilder.aProduct().withBrand(brand).build());
        assertThrows(ConstraintViolationException.class, () -> productService.updateProduct(savedProduct.getId(), productRequest));
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Should throw exception when update product with the same parameters")
    void shouldThrowExceptionWhenNewProductIsAlreadyExist() {
        final ProductRequest productRequest = ProductRequestBuilder.aProductRequest().withBrandId(brand.getId()).build();
        final ProductEntity savedProduct = productService.createProduct(productRequest);
        assertThrows(InvalidObjectException.class, () -> productService.updateProduct(savedProduct.getId(), productRequest));
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Should delete product")
    void shouldDeleteProduct() {
        final ProductEntity savedProduct = productRepository.save(ProductBuilder.aProduct().withBrand(brand).build());
        productService.deleteProduct(savedProduct.getId());
        assertThrows(ObjectNotFoundException.class, () -> productService.getProductById(savedProduct.getId()));
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Should throw exception when delete not existing product")
    void shouldThrowExceptionWhenDeleteNotExistingProduct() {
        assertThrows(InvalidObjectException.class, () -> productService.deleteProduct(UUID.randomUUID()));
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Should throw exception when stock items exist with product")
    void shouldThrowExceptionWhenStockItemExistWithProduct() {
        final ProductEntity savedProduct = productRepository.save(ProductBuilder.aProduct().withBrand(brand).build());
        final StockEntity savedStockItem = stockRepository.save(StockItemBuilder.aStockItem()
                .withProduct(savedProduct)
                .withAmount(100L)
                .build());
        assertThrows(InvalidObjectException.class, () -> productService.deleteProduct(savedProduct.getId()));
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Should delete when stock item amount is zero")
    void shouldDeleteStockItemAmountIsZero() {
        final ProductEntity savedProduct = productRepository.save(ProductBuilder.aProduct().withBrand(brand).build());
        final StockEntity savedStockItem = stockRepository.save(StockItemBuilder.aStockItem()
                .withProduct(savedProduct)
                .withAmount(0L)
                .build());
        productService.deleteProduct(savedProduct.getId());
        assertThrows(ObjectNotFoundException.class, () -> productService.getProductById(savedProduct.getId()));
        assertThrows(ObjectNotFoundException.class, () -> stockRepository.getById(savedStockItem.getId()));
    }
}
