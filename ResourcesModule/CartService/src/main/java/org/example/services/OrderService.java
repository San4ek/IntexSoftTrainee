package org.example.services;

import jakarta.validation.Valid;
import org.example.dtos.OrderRequest;
import org.example.dtos.OrderResponse;
import org.example.entities.OrderEntity;

import java.util.UUID;

/**
 * Interface for orders.
 */
public interface OrderService {

    OrderEntity getOrder(UUID orderId);

    OrderResponse createOrder(@Valid OrderRequest orderRequest);

    void deleteOrdersWithAddress(UUID addressId);

    void deleteOrderWithId(UUID orderId);
}
