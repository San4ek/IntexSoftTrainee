package org.example.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.example.controllers.OrderOperationsController;
import org.example.dtos.OrderRequest;
import org.example.dtos.OrderResponse;
import org.example.exceptions.EndpointNotImplementedException;
import org.example.mappers.OrderMapper;
import org.example.services.OrderService;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * REST controller for managing orders.
 */
@RestController
@RequiredArgsConstructor
public class OrderController implements OrderOperationsController {

    private final OrderMapper orderMapper;
    private final OrderService orderService;

    /**
     * Retrieve the details of a specific order by its unique identifier.
     *
     * @param orderId the unique identifier of the order to retrieve.
     * @return an {@link OrderResponse} containing the details of the order.
     */
    @Override
    public OrderResponse getOrder(final UUID orderId) {
        return orderMapper.toDto(orderService.getOrder(orderId));
    }

    /**
     * Create a new order.
     *
     * @param orderRequest the request object containing the details of the order to create.
     * @return the created {@link OrderResponse}.
     */
    @Override
    public OrderResponse createOrder(final OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    /**
     * Delete all orders associated with a specific address by the address's unique identifier.
     *
     * @param addressId the unique identifier of the address.
     */
    @Override
    public void deleteOrdersWithAddress(final UUID addressId) {
        orderService.deleteOrdersWithAddress(addressId);
    }
}
