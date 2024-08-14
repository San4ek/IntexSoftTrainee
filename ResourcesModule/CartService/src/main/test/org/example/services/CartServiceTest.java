package org.example.services;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import lombok.RequiredArgsConstructor;
import org.example.dtos.CartItemRequest;
import org.example.dtos.CartRequest;
import org.example.dtos.StockItemAmount;
import org.example.entities.CartEntity;
import org.example.entities.CartItemEntity;
import org.example.exceptions.InvalidObjectException;
import org.example.exceptions.ObjectNotFoundException;
import org.example.repositories.CartItemRepository;
import org.example.repositories.CartRepository;
import org.example.services.impl.StockServiceImpl;
import org.example.testutils.CartBuilder;
import org.example.testutils.CartItemBuilder;
import org.example.testutils.CartItemRequestBuilder;
import org.example.testutils.CartRequestBuilder;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@Testcontainers
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CartServiceTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @MockBean
    private StockServiceImpl stockService;

    private final CartService cartService;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private CartEntity cart;
    private CartItemEntity cartItem;

    @BeforeEach
    void setUp() {
        cart = cartRepository.save(CartBuilder.aCart().build());
        cartItem = cartItemRepository.save(CartItemBuilder.aCartItem().withCart(cart).build());
        cart.addItems(cartItem);
        cartRepository.save(cart);
    }

    @Test
    @WithJwt("resources/jwt/user.json")
    @DisplayName("Should retrieve cart by ID")
    void shouldGetCartById() {
        final CartEntity foundCart = cartService.getCart(cart.getId());
        assertNotNull(foundCart);
        assertEquals(cart, foundCart);
    }

    @Test
    @WithJwt("resources/jwt/user.json")
    @DisplayName("Should create a new cart")
    void shouldCreateCart() {
        final CartRequest cartRequest = CartRequestBuilder.aCartRequest().build();
        final CartEntity newCart = cartService.createCart(cartRequest);
        assertNotNull(newCart);
        assertEquals(cartRequest.getCurrency(), newCart.getCurrency());
    }

    @Test
    @WithJwt("resources/jwt/user.json")
    @DisplayName("Should update an existing cart")
    void shouldUpdateCart() {
        final CartRequest cartRequest = CartRequestBuilder.aCartRequest().build();
        final CartEntity updatedCart = cartService.updateCart(cart.getId(), cartRequest);
        assertNotNull(updatedCart);
        assertEquals(cartRequest.getCurrency(), updatedCart.getCurrency());
    }

    @Test
    @WithJwt("resources/jwt/user.json")
    @DisplayName("Should add an item to the cart")
    void shouldAddItemInCart() {
        final CartItemRequest cartItemRequest = CartItemRequestBuilder.aCartItemRequest().withCartId(cart.getId()).build();
        final StockItemAmount stockItemAmount = new StockItemAmount(cartItemRequest.getStockId(), cartItemRequest.getAmount() + 100L, 19.99D);
        when(stockService.getStockItemById(any(UUID.class))).thenReturn(stockItemAmount);
        final CartItemEntity addedItem = cartService.addItemInCart(cartItemRequest);
        assertNotNull(addedItem);
        assertEquals(cartItemRequest.getStockId(), addedItem.getStockId());
        assertEquals(cartItemRequest.getAmount(), addedItem.getAmount());
    }

    @Test
    @WithJwt("resources/jwt/user.json")
    @DisplayName("Should delete an item from the cart")
    void shouldDeleteItemFromCart() {
        cartService.deleteItemFromCart(cart.getId(), cartItem.getStockId());
        assertThrows(ObjectNotFoundException.class, () -> cartItemRepository.getById(cartItem.getId()));
    }

    @Test
    @WithJwt("resources/jwt/user.json")
    @DisplayName("Should delete a cart")
    void shouldDeleteCart() {
        cartService.deleteCart(cart.getId());
        assertThrows(ObjectNotFoundException.class, () -> cartRepository.getById(cart.getId()));
    }

    @Test
    @WithJwt("resources/jwt/user.json")
    @DisplayName("Should delete all items with the specified stock ID from all carts")
    void shouldDeleteCartItemsByStockId() {
        cartService.deleteCartItemsByStockId(cartItem.getStockId());
        assertThrows(ObjectNotFoundException.class, () -> cartItemRepository.getById(cartItem.getId()));
    }

    @Test
    @WithJwt("resources/jwt/user.json")
    @DisplayName("Should throw exception when retrieving non-existing cart by ID")
    void shouldThrowExceptionWhenGettingNonExistingCartById() {
        assertThrows(ObjectNotFoundException.class, () -> cartService.getCart(UUID.randomUUID()));
    }

    @Test
    @WithJwt("resources/jwt/user.json")
    @DisplayName("Should throw exception when updating non-existing cart")
    void shouldThrowExceptionWhenUpdatingNonExistingCart() {
        final CartRequest cartRequest = CartRequestBuilder.aCartRequest().build();
        assertThrows(InvalidObjectException.class, () -> cartService.updateCart(UUID.randomUUID(), cartRequest));
    }

    @Test
    @WithJwt("resources/jwt/user.json")
    @DisplayName("Should throw exception when adding an item to a non-existing cart")
    void shouldThrowExceptionWhenAddingItemToNonExistingCart() {
        final CartItemRequest cartItemRequest = CartItemRequestBuilder.aCartItemRequest().withCartId(UUID.randomUUID()).build();
        assertThrows(InvalidObjectException.class, () -> cartService.addItemInCart(cartItemRequest));
    }

    @Test
    @WithJwt("resources/jwt/user.json")
    @DisplayName("Should throw exception when deleting item from non-existing cart")
    void shouldThrowExceptionWhenDeletingItemFromNonExistingCart() {
        assertThrows(InvalidObjectException.class, () -> cartService.deleteItemFromCart(UUID.randomUUID(), cartItem.getStockId()));
    }

    @Test
    @WithJwt("resources/jwt/user.json")
    @DisplayName("Should throw exception when deleting a non-existing cart")
    void shouldThrowExceptionWhenDeletingNonExistingCart() {
        assertThrows(InvalidObjectException.class, () -> cartService.deleteCart(UUID.randomUUID()));
    }
}
