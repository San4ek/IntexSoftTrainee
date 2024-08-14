package org.example.services;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import lombok.RequiredArgsConstructor;
import org.example.dtos.OrderRequest;
import org.example.dtos.OrderResponse;
import org.example.dtos.SendMailRequest;
import org.example.dtos.StockItemAmount;
import org.example.entities.AddressEntity;
import org.example.entities.CartEntity;
import org.example.entities.CartItemEntity;
import org.example.entities.OrderEntity;
import org.example.exceptions.InvalidObjectException;
import org.example.exceptions.ObjectNotFoundException;
import org.example.repositories.AddressRepository;
import org.example.repositories.CartItemRepository;
import org.example.repositories.CartRepository;
import org.example.repositories.OrderRepository;
import org.example.services.impl.OrderServiceImpl;
import org.example.services.impl.StockServiceImpl;
import org.example.testutils.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@Testcontainers
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OrderServiceTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @MockBean
    private StockServiceImpl stockService;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    private final OrderServiceImpl orderService;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AddressRepository addressRepository;
    private CartItemEntity cartItem;
    private CartEntity cart;
    private OrderEntity order;
    private AddressEntity address;

    @BeforeEach
    void setUp() {
        cart = cartRepository.save(CartBuilder.aCart().build());
        cartItem = cartItemRepository.save(CartItemBuilder.aCartItem().withCart(cart).build());
        cart.addItems(cartItem);
        cartRepository.save(cart);
        address = addressRepository.save(AddressBuilder.anAddress().build());
        order = orderRepository.save(OrderBuilder.anOrder().withCart(cart).withAddress(address).build());
    }

    @Test
    @WithJwt("resources/jwt/user.json")
    @DisplayName("Should retrieve an order by its ID")
    void shouldRetrieveOrderById() {
        final OrderEntity retrievedOrder = orderService.getOrder(order.getId());
        assertNotNull(retrievedOrder);
        assertEquals(order.getId(), retrievedOrder.getId());
    }

    @Test
    @WithJwt("resources/jwt/user.json")
    @DisplayName("Should create a new order from the specified cart")
    void shouldCreateNewOrder() {
        when(stockService.getStockItemById(any(UUID.class))).thenReturn(new StockItemAmount(cartItem.getStockId(), 100L, 19.99D));
        final OrderRequest orderRequest = OrderRequestBuilder.anOrderRequest()
                .withCartId(cart.getId())
                .withAddressId(address.getId())
                .build();
        doNothing().when(rabbitTemplate).convertAndSend(eq("mail.send"), eq(""), any(SendMailRequest.class));
        final OrderResponse orderResponse = orderService.createOrder(orderRequest);
        assertNotNull(orderResponse);
        assertEquals(orderRequest.getCartId(), orderResponse.getCart().getId());
        assertTrue(cartRepository.getById(cart.getId()).getCartItems().isEmpty(), "Cart should be empty after order creation");
    }

    @Test
    @WithJwt("resources/jwt/user.json")
    @DisplayName("Should throw exception when creating an order with an empty cart")
    void shouldThrowExceptionWhenCreatingOrderWithEmptyCart() {
        final CartEntity emptyCart = cartRepository.save(CartBuilder.aCart().build());
        final OrderRequest orderRequest = OrderRequestBuilder.anOrderRequest().withCartId(emptyCart.getId()).build();
        assertThrows(InvalidObjectException.class, () -> orderService.createOrder(orderRequest));
    }

    @Test
    @WithJwt("resources/jwt/user.json")
    @DisplayName("Should delete an order by its ID")
    void shouldDeleteOrderById() {
        orderService.deleteOrderWithId(order.getId());
        assertThrows(ObjectNotFoundException.class, () -> orderService.getOrder(order.getId()));
    }

    @Test
    @WithJwt("resources/jwt/user.json")
    @DisplayName("Should throw exception when deleting a non-existing order")
    void shouldThrowExceptionWhenDeletingNonExistingOrder() {
        assertThrows(InvalidObjectException.class, () -> orderService.deleteOrderWithId(UUID.randomUUID()));
    }

    @Test
    @WithJwt("resources/jwt/user.json")
    @DisplayName("Should delete all orders with the specified address ID")
    void shouldDeleteOrdersWithAddressId() {
        orderService.deleteOrdersWithAddress(address.getId());
        assertThrows(ObjectNotFoundException.class, () -> orderService.getOrder(order.getId()));
    }

    @Test
    @WithJwt("resources/jwt/user.json")
    @DisplayName("Should throw exception when deleting orders with a non-existing address")
    void shouldThrowExceptionWhenDeletingOrdersForNonExistingAddress() {
        assertThrows(InvalidObjectException.class, () -> orderService.deleteOrdersWithAddress(UUID.randomUUID()));
    }
}
