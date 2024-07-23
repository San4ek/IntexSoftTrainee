package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dtos.OrderRequest;
import org.example.dtos.OrderResponse;
import org.example.entities.CartEntity;
import org.example.entities.OrderEntity;
import org.example.mappers.OrderMapper;
import org.example.repositories.CartRepository;
import org.example.repositories.OrderRepository;
import org.example.services.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.example.utils.validation.ValidatorUtils.checkFalse;

/**
 * Service implementation for orders.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CartRepository cartRepository;

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId the ID of the order to retrieve.
     * @return the order entity.
     */
    @Override
    @Transactional(readOnly = true)
    public OrderEntity getOrder(final UUID orderId) {
        return orderRepository.getById(orderId);
    }

    /**
     * Creates a new order from the specified cart.
     *
     * @param orderRequest the request object containing order details.
     * @return the created order response.
     */
    @Override
    @Transactional
    public OrderResponse createOrder(final OrderRequest orderRequest) {
        log.info("Create order from cart {} ", orderRequest.getCartId());
        CartEntity cartEntity = cartRepository.getById(orderRequest.getCartId());
        checkFalse(cartEntity.getCartItems().isEmpty(), "Cart is empty");
        OrderResponse orderResponse = orderMapper.toDto(orderRepository.save(orderMapper.toEntity(orderRequest)));
        cartEntity.removeAllItems();
        cartRepository.save(cartEntity);
        log.info("Order created with id {}", orderResponse.getId());
        return orderResponse;
    }

    /**
     * Deletes all orders with specified address id.
     *
     * @param addressId the ID of the address to delete.
     */
    @Override
    @Transactional
    public void deleteOrdersWithAddress(final UUID addressId) {
        log.info("Delete orders with address {}", addressId);
        orderRepository.deleteByAddressId(addressId);
    }
}
