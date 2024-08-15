package org.example.services;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import lombok.RequiredArgsConstructor;
import org.example.dtos.StockItemAmount;
import org.example.entities.BrandEntity;
import org.example.entities.ProductEntity;
import org.example.entities.StockEntity;
import org.example.enums.StockOperationEnum;
import org.example.exceptions.InvalidObjectException;
import org.example.exceptions.ObjectNotFoundException;
import org.example.repositories.BrandRepository;
import org.example.repositories.ProductRepository;
import org.example.repositories.StockRepository;
import org.example.services.impl.StockUserServiceImpl;
import org.example.testutils.BrandBuilder;
import org.example.testutils.ProductBuilder;
import org.example.testutils.StockItemBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.Page;
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
public class StockUserServiceTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    private final StockUserServiceImpl stockUserService;
    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private ProductEntity product;
    private StockEntity stockItem;

    @BeforeEach
    void setUp() {
        final BrandEntity brand = brandRepository.save(BrandBuilder.aBrand().build());
        product = productRepository.save(ProductBuilder.aProduct().withBrand(brand).build());
        stockItem = stockRepository.save(StockItemBuilder.aStockItem().withProduct(product).withAmount(100L).build());
    }

    @Test
    @WithJwt("resources/jwt/user.json")
    @DisplayName("Should find stock items by name")
    void shouldFindStockItemsByName() {
        final Page<StockEntity> foundStockItems = stockUserService.findStockItemsByName(product.getName(), 0, 10);
        assertNotNull(foundStockItems);
        assertEquals(1, foundStockItems.getTotalElements());
        assertEquals(stockItem, foundStockItems.getContent().get(0));
    }

    @Test
    @WithJwt("resources/jwt/user.json")
    @DisplayName("Should return empty page when stock items by name are not found")
    void shouldReturnEmptyPageWhenStockItemsByNameAreNotFound() {
        final Page<StockEntity> foundStockItems = stockUserService.findStockItemsByName("Invalid Name", 0, 10);
        assertNotNull(foundStockItems);
        assertEquals(0, foundStockItems.getTotalElements());
    }

    @Test
    @WithJwt("resources/jwt/user.json")
    @DisplayName("Should find stock items by attributes")
    void shouldFindStockItemsByAttributes() {
        final Page<StockEntity> foundStockItems = stockUserService.findByAttributes(
                product.getBrand().getName(),
                stockItem.getColor(),
                stockItem.getSize(),
                product.getType(),
                null,
                null,
                0,
                10
        );
        assertNotNull(foundStockItems);
        assertEquals(1, foundStockItems.getTotalElements());
        assertEquals(stockItem, foundStockItems.getContent().get(0));
    }

    @Test
    @WithJwt("resources/jwt/user.json")
    @DisplayName("Should return empty page when stock items by attributes are not found")
    void shouldReturnEmptyPageWhenStockItemsByAttributesAreNotFound() {
        final Page<StockEntity> foundStockItems = stockUserService.findByAttributes(
                "Invalid Brand",
                stockItem.getColor(),
                stockItem.getSize(),
                product.getType(),
                null,
                null,
                0,
                10
        );
        assertNotNull(foundStockItems);
        assertEquals(0, foundStockItems.getTotalElements());
    }

    @Test
    @WithJwt("resources/jwt/user.json")
    @DisplayName("Should check stock item amount")
    void shouldCheckStockItemAmount() {
        final StockItemAmount stockItemAmount = stockUserService.checkStockItemAmount(stockItem.getId());
        assertNotNull(stockItemAmount);
        assertEquals(stockItem.getAmount(), stockItemAmount.getAmount());
    }

    @Test
    @WithJwt("resources/jwt/user.json")
    @DisplayName("Should throw exception when checking amount for non-existing stock item")
    void shouldThrowExceptionWhenCheckingAmountForNonExistingStockItem() {
        assertThrows(ObjectNotFoundException.class, () -> stockUserService.checkStockItemAmount(UUID.randomUUID()));
    }

    @Test
    @WithJwt("resources/jwt/user.json")
    @DisplayName("Should increase stock item amount")
    void shouldIncreaseStockItemAmount() {
        final Long increaseAmount = 50L;
        final Long initialAmount = stockItem.getAmount();
        stockUserService.changeStockItemAmount(stockItem.getId(), increaseAmount, StockOperationEnum.INCREASE);
        final StockEntity updatedStockItem = stockRepository.getById(stockItem.getId());
        assertEquals(initialAmount + increaseAmount, updatedStockItem.getAmount());
    }

    @Test
    @WithJwt("resources/jwt/user.json")
    @DisplayName("Should decrease stock item amount")
    void shouldDecreaseStockItemAmount() {
        final Long decreaseAmount = 50L;
        final Long initialAmount = stockItem.getAmount();
        stockUserService.changeStockItemAmount(stockItem.getId(), decreaseAmount, StockOperationEnum.DECREASE);
        final StockEntity updatedStockItem = stockRepository.getById(stockItem.getId());
        assertEquals(initialAmount - decreaseAmount, updatedStockItem.getAmount());
    }

    @Test
    @WithJwt("resources/jwt/user.json")
    @DisplayName("Should throw exception when decreasing stock item amount below zero")
    void shouldThrowExceptionWhenDecreasingStockItemAmountBelowZero() {
        final Long decreaseAmount = stockItem.getAmount() + 10L;
        assertThrows(InvalidObjectException.class, () -> stockUserService.changeStockItemAmount(stockItem.getId(), decreaseAmount, StockOperationEnum.DECREASE));
    }
}
