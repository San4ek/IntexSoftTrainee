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
     * Retrieve the order by its id.
     *
     * @param orderId the id of the order to retrieve.
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
     * Delete order with specified id.
     *
     * @param orderId the id of the order to delete
     */
    @Override
    public void deleteOrder(final UUID orderId) {
        orderService.deleteOrderWithId(orderId);
    }

    /**
     * Delete all orders by the address's id.
     *
     * @param addressId the id of the address.
     */
    @Override
    public void deleteOrdersWithAddress(final UUID addressId) {
        orderService.deleteOrdersWithAddress(addressId);
    }
}
