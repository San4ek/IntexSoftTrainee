package org.example.services;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.example.dtos.StockItemRequest;
import org.example.entities.BrandEntity;
import org.example.entities.ProductEntity;
import org.example.entities.StockEntity;
import org.example.enums.ColorEnum;
import org.example.exceptions.InvalidObjectException;
import org.example.exceptions.ObjectNotFoundException;
import org.example.repositories.BrandRepository;
import org.example.repositories.ProductRepository;
import org.example.repositories.StockRepository;
import org.example.services.client.CartItemsClient;
import org.example.services.impl.StockModeratorServiceImpl;
import org.example.testutils.BrandBuilder;
import org.example.testutils.ProductBuilder;
import org.example.testutils.StockItemBuilder;
import org.example.testutils.StockItemRequestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@Transactional
@Testcontainers
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class StockModeratorServiceTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @MockBean
    private CartItemsClient cartItemsClient;

    private final StockModeratorServiceImpl stockModeratorService;
    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private ProductEntity product;

    @BeforeEach
    void setUp() {
        final BrandEntity brand = brandRepository.save(BrandBuilder.aBrand().build());
        product = productRepository.save(ProductBuilder.aProduct().withBrand(brand).build());
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Should find stock item by id")
    void shouldFindStockItemById() {
        final StockEntity savedStockItem = stockRepository.save(StockItemBuilder.aStockItem().withProduct(product).build());
        final StockEntity foundStockItem = stockModeratorService.findById(savedStockItem.getId());
        assertEquals(savedStockItem, foundStockItem);
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Should throw exception when stock item not found by id")
    void shouldThrowExceptionWhenStockItemNotFoundById() {
        assertThrows(ObjectNotFoundException.class, () -> stockModeratorService.findById(UUID.randomUUID()));
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Should create stock item")
    void shouldCreateStockItem() {
        final StockItemRequest stockItemRequest = StockItemRequestBuilder.aStockItemRequest().withProductId(product.getId()).build();
        final StockEntity createdStockItem = stockModeratorService.createStockItem(stockItemRequest);
        assertEquals(stockItemRequest.getProductId(), createdStockItem.getProduct().getId());
        assertEquals(stockItemRequest.getColor(), createdStockItem.getColor());
        assertEquals(stockItemRequest.getSize(), createdStockItem.getSize());
        assertEquals(stockItemRequest.getAmount(), createdStockItem.getAmount());
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Should throw exception when creating stock item with invalid data")
    void shouldThrowExceptionWhenCreatingInvalidStockItem() {
        final StockItemRequest stockItemRequest = StockItemRequestBuilder.aStockItemRequest().withAmount(-1L).build();
        assertThrows(ConstraintViolationException.class, () -> stockModeratorService.createStockItem(stockItemRequest));
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Should update stock item")
    void shouldUpdateStockItem() {
        final StockEntity savedStockItem = stockRepository.save(StockItemBuilder.aStockItem()
                .withProduct(product)
                .withColor(ColorEnum.RED)
                .build());
        final StockItemRequest updateStockItemRequest = StockItemRequestBuilder.aStockItemRequest()
                .withProductId(product.getId())
                .withColor(ColorEnum.BLACK)
                .build();
        final StockEntity updatedStockItem = stockModeratorService.updateStockItem(savedStockItem.getId(), updateStockItemRequest);
        assertEquals(updateStockItemRequest.getColor(), updatedStockItem.getColor());
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Should throw exception when updating stock item with invalid data")
    void shouldThrowExceptionWhenUpdatingStockItemWithInvalidData() {
        final StockEntity savedStockItem = stockRepository.save(StockItemBuilder.aStockItem().withProduct(product).build());
        final StockItemRequest updateStockItemRequest = StockItemRequestBuilder.aStockItemRequest().withAmount(-10L).build();
        assertThrows(ConstraintViolationException.class, () -> stockModeratorService.updateStockItem(savedStockItem.getId(), updateStockItemRequest));
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Should remove stock items by setting amount to zero")
    void shouldRemoveStockItems() {
        final StockEntity savedStockItem = stockRepository.save(StockItemBuilder.aStockItem()
                .withProduct(product)
                .withAmount(100L)
                .build());
        doNothing().when(cartItemsClient).deleteCartItemsWithStockId(savedStockItem.getId());
        stockModeratorService.removeStockItems(savedStockItem.getId());
        assertEquals(0L, savedStockItem.getAmount());
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Should delete stock item")
    void shouldDeleteStockItem() {
        final StockEntity savedStockItem = stockRepository.save(StockItemBuilder.aStockItem()
                .withProduct(product)
                .withAmount(0L)
                .build());
        stockModeratorService.deleteStockItem(savedStockItem.getId());
        assertThrows(ObjectNotFoundException.class, () -> stockRepository.getById(savedStockItem.getId()));
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Should throw exception when deleting non-existing stock item")
    void shouldThrowExceptionWhenDeletingNonExistingStockItem() {
        assertThrows(InvalidObjectException.class, () -> stockModeratorService.deleteStockItem(UUID.randomUUID()));
    }
}
