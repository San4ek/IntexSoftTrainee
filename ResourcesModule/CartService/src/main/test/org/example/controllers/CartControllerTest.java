package org.example.controllers;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.dtos.CartItemRequest;
import org.example.dtos.CartRequest;
import org.example.entities.CartEntity;
import org.example.entities.CartItemEntity;
import org.example.repositories.CartRepository;
import org.example.services.CartService;
import org.example.services.impl.CartServiceImpl;
import org.example.testutils.*;
import org.example.validation.ValidationCartService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Testcontainers
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CartControllerTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @MockBean
    private CartServiceImpl cartService;

    @MockBean
    private ValidationCartService validationCartService;

    private final MockMvc mockMvc;
    private final CartRepository cartRepository;
    private final ObjectMapper mapper;
    private CartEntity cartEntity;
    private CartRequest cartRequest;
    private CartItemRequest cartItemRequest;

    @BeforeEach
    void setUp() {
        cartRequest = CartRequestBuilder.aCartRequest().build();
        cartEntity = cartRepository.save(CartBuilder.aCart().build());
        cartItemRequest = CartItemRequestBuilder.aCartItemRequest().withCartId(cartEntity.getId()).build();
    }

    @Test
    @WithJwt("resources/jwt/moder.json")
    @DisplayName("Tests GET /api/carts/{cartId} with valid ID")
    void getCart_200Expected() throws Exception {
        final UUID cartId = cartEntity.getId();
        when(cartService.getCart(cartId)).thenReturn(cartEntity);
        mockMvc.perform(get(EndpointsUrls.CART_GET_PUT_DELETE.getPath() + cartId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cartId.toString()));
    }

    @Test
    @WithJwt("resources/jwt/moder.json")
    @DisplayName("Tests POST /api/carts with valid body")
    void createCart_201Expected() throws Exception {
        when(cartService.createCart(any(CartRequest.class))).thenReturn(cartEntity);
        mockMvc.perform(post(EndpointsUrls.CART_POST.getPath())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(cartRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(cartEntity.getId().toString()));
    }

    @Test
    @WithJwt("resources/jwt/moder.json")
    @DisplayName("Tests PUT /api/carts/{cartId} with valid body")
    void updateCart_200Expected() throws Exception {
        final UUID cartId = cartEntity.getId();
        when(cartService.updateCart(any(UUID.class), any(CartRequest.class))).thenReturn(cartEntity);
        mockMvc.perform(put(EndpointsUrls.CART_GET_PUT_DELETE.getPath() + cartId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(cartRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cartId.toString()));
    }

    @Test
    @WithJwt("resources/jwt/moder.json")
    @DisplayName("Tests POST /api/carts/item with valid body")
    void addItemInCart_201Expected() throws Exception {
        final CartItemEntity cartItemEntity = CartItemBuilder.aCartItem().withCart(cartEntity).build();
        when(cartService.addItemInCart(any(CartItemRequest.class))).thenReturn(cartItemEntity);
        mockMvc.perform(post(EndpointsUrls.CART_ADD_REMOVE_ITEM.getPath())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(cartItemRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithJwt("resources/jwt/moder.json")
    @DisplayName("Tests DELETE /api/carts/item with valid IDs")
    void deleteItemFromCart_204Expected() throws Exception {
        final UUID cartId = cartEntity.getId();
        final UUID stockItemId = cartItemRequest.getStockId();
        doNothing().when(cartService).deleteItemFromCart(cartId, stockItemId);
        mockMvc.perform(delete(EndpointsUrls.CART_ADD_REMOVE_ITEM.getPath())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("cartId", cartId.toString())
                        .param("stockItemId", stockItemId.toString()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithJwt("resources/jwt/moder.json")
    @DisplayName("Tests DELETE /api/carts/items/stock/{stockId} with valid stock ID and API key")
    void deleteCartItemsWithStockId_204Expected() throws Exception {
        final UUID stockId = UUID.randomUUID();
        String apiKey = "validApiKey";
        doNothing().when(cartService).deleteCartItemsByStockId(stockId);
        doNothing().when(validationCartService).validateApiKey(apiKey);
        mockMvc.perform(delete(EndpointsUrls.CART_DELETE_BY_STOCK_ID.getPath() + stockId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("STOCK-API-KEY", apiKey))
                .andExpect(status().isNoContent());
    }
}
