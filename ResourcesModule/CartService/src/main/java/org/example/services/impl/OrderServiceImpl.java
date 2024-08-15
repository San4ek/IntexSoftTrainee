package org.example.services.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dtos.OrderRequest;
import org.example.dtos.OrderResponse;
import org.example.dtos.SendMailRequest;
import org.example.entities.CartEntity;
import org.example.entities.OrderEntity;
import org.example.mappers.OrderMapper;
import org.example.repositories.CartRepository;
import org.example.repositories.OrderRepository;
import org.example.services.OrderService;
import org.example.validation.ValidationOrderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

import static org.example.utils.validation.ValidatorUtils.checkFalse;

/**
 * Service implementation for orders.
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CartRepository cartRepository;
    private final ValidationOrderService validationOrderService;
    private final RabbitTemplate rabbitTemplate;

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
    public OrderResponse createOrder(@Valid final OrderRequest orderRequest) {
        log.info("Create order from cart {} ", orderRequest.getCartId());
        validationOrderService.validateOrderForCreating(orderRequest);
        CartEntity cartEntity = cartRepository.getById(orderRequest.getCartId());
        OrderResponse orderResponse = orderMapper.toDto(orderRepository.save(orderMapper.toEntity(orderRequest)));
        cartEntity.removeAllItems();
        cartRepository.save(cartEntity);
        log.info("Order created with id {}", orderResponse.getId());
        SendMailRequest sendMailRequest = new SendMailRequest(cartEntity.getUserId(), "Order Created", "Your order has been created");
        rabbitTemplate.convertAndSend("mail.send", "", sendMailRequest);
        return orderResponse;
    }

    /**
     * Deletes order with specified id
     *
     * @param orderId the ID of the order to delete
     */
    @Override
    public void deleteOrderWithId(final UUID orderId) {
        log.info("Delete order with id {}", orderId);
        validationOrderService.validateOrderForDeleting(orderId);
        orderRepository.deleteById(orderId);
    }

    /**
     * Deletes all orders with specified address id.
     *
     * @param addressId the ID of the order address to delete.
     */
    @Override
    @Transactional
    public void deleteOrdersWithAddress(final UUID addressId) {
        log.info("Delete orders with address {}", addressId);
        validationOrderService.validateOrdersWithAddressIdForDeleting(addressId);
        orderRepository.deleteByAddressId(addressId);
    }
}
