package org.example.controllers;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.dtos.OrderRequest;
import org.example.entities.OrderEntity;
import org.example.mappers.OrderMapper;
import org.example.services.impl.OrderServiceImpl;
import org.example.testutils.EndpointsUrls;
import org.example.testutils.OrderBuilder;
import org.example.testutils.OrderRequestBuilder;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Testcontainers
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OrderControllerTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @MockBean
    private OrderServiceImpl orderService;

    private final MockMvc mockMvc;
    private final OrderMapper orderMapper;
    private final ObjectMapper mapper;
    private OrderEntity orderEntity;
    private OrderRequest orderRequest;

    @BeforeEach
    void setUp() {
        orderRequest = OrderRequestBuilder.anOrderRequest().build();
        orderEntity = OrderBuilder.anOrder().build();
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Tests GET /api/orders/{orderId} with valid ID")
    void getOrder_200Expected() throws Exception {
        final UUID orderId = orderEntity.getId();
        when(orderService.getOrder(orderId)).thenReturn(orderEntity);
        mockMvc.perform(get(EndpointsUrls.ORDERS_GET_DELETE.getPath() + orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId.toString()));
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Tests POST /api/orders with valid body")
    void createOrder_201Expected() throws Exception {
        when(orderService.createOrder(any(OrderRequest.class))).thenReturn(orderMapper.toDto(orderEntity));
        mockMvc.perform(post(EndpointsUrls.ORDERS_POST.getPath())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(orderRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Tests DELETE /api/orders/{orderId} with valid ID")
    void deleteOrder_204Expected() throws Exception {
        final UUID orderId = orderEntity.getId();
        doNothing().when(orderService).deleteOrderWithId(orderId);
        mockMvc.perform(delete(EndpointsUrls.ORDERS_GET_DELETE.getPath() + orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Tests DELETE /api/orders/address/{addressId} with valid ID")
    void deleteOrdersWithAddress_204Expected() throws Exception {
        final UUID addressId = orderEntity.getAddress().getId();
        doNothing().when(orderService).deleteOrdersWithAddress(addressId);
        mockMvc.perform(delete(EndpointsUrls.ORDERS_DELETE_BY_ADDRESS.getPath() + addressId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithJwt("jwt/moder.json")
    @DisplayName("Tests POST /api/orders without body")
    void createOrder_400Expected() throws Exception {
        mockMvc.perform(post(EndpointsUrls.ORDERS_POST.getPath())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Tests POST /api/orders without auth")
    void createOrder_401Expected() throws Exception {
        mockMvc.perform(post(EndpointsUrls.ORDERS_POST.getPath()))
                .andExpect(status().isUnauthorized());
    }
}
