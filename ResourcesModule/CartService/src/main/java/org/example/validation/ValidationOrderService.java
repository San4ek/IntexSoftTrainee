package org.example.validation;

import lombok.RequiredArgsConstructor;
import org.example.dtos.OrderRequest;
import org.example.exceptions.InvalidObjectException;
import org.example.repositories.AddressRepository;
import org.example.repositories.CartRepository;
import org.example.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.example.utils.validation.ValidatorUtils.checkFalse;
import static org.example.utils.validation.ValidatorUtils.checkTrue;

/**
 * Service for validating orders.
 */
@Service
@RequiredArgsConstructor
public class ValidationOrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;

    /**
     * Validates the order request for creating an order.
     *
     * @param orderRequest the order request to validate
     * @throws InvalidObjectException if the cart does not exist, the cart is empty, or the address does not exist
     */
    public void validateOrderForCreating(final OrderRequest orderRequest) {
        checkTrue(cartRepository.existsById(orderRequest.getCartId()), "Cart is not exist");
        checkFalse(cartRepository.getById(orderRequest.getCartId()).getCartItems().isEmpty(), "Cart can't be empty");
        checkTrue(addressRepository.existsById(orderRequest.getAddressId()), "Address is not exist");
    }

    /**
     * Validates the order for deleting.
     *
     * @param orderId the ID of the order to validate
     * @throws InvalidObjectException if the order does not exist
     */
    public void validateOrderForDeleting(final UUID orderId) {
        checkTrue(orderRepository.existsById(orderId));
    }

    /**
     * Validates the orders with the address ID for deleting.
     *
     * @param addressId the ID of the address to validate
     * @throws InvalidObjectException if no orders exist with the provided address ID
     */
    public void validateOrdersWithAddressIdForDeleting(final UUID addressId) {
        checkTrue(orderRepository.existsByAddressId(addressId), "Addresses with this id are not exist");
    }
}
