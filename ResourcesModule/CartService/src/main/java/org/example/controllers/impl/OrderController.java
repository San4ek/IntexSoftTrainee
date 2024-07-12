package org.example.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.example.controllers.OrderOperationsController;
import org.example.dtos.OrderRequest;
import org.example.dtos.OrderResponse;
import org.example.mappers.OrderMapper;
import org.example.services.OrderService;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * REST controller for orders.
 */
@RestController
@RequiredArgsConstructor
public class OrderController implements OrderOperationsController {

    private final OrderMapper orderMapper;
    private final OrderService orderService;

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId the ID of the order to retrieve.
     * @return the order response containing order details.
     */
    @Override
    public OrderResponse getOrder(final UUID orderId) {
        return orderMapper.toDto(orderService.getOrder(orderId));
    }

    /**
     * Creates a new order from the specified request.
     *
     * @param orderRequest the request object containing order details.
     * @return the response containing the created order details.
     */
    @Override
    public OrderResponse createOrder(final OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }
}
