package org.example.validation;

import lombok.RequiredArgsConstructor;
import org.example.dtos.OrderRequest;
import org.example.repositories.AddressRepository;
import org.example.repositories.CartRepository;
import org.example.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.example.utils.validation.ValidatorUtils.checkFalse;
import static org.example.utils.validation.ValidatorUtils.checkTrue;

@Service
@RequiredArgsConstructor
public class ValidationOrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;

    public void validateOrderForCreating(final OrderRequest orderRequest) {
        checkTrue(cartRepository.existsById(orderRequest.getCartId()), "Cart is not exist");
        checkFalse(cartRepository.getById(orderRequest.getCartId()).getCartItems().isEmpty(), "Cart can't be empty");
        checkTrue(addressRepository.existsById(orderRequest.getAddressId()), "Address is not exist");
    }

    public void validateOrdersWithAddressIdForDeleting(final UUID addressId) {
        checkTrue(orderRepository.existsByAddressId(addressId), "Addresses with this id are not exist");
    }


}
