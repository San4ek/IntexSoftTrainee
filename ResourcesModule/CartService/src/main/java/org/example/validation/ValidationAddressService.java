package org.example.validation;

import lombok.RequiredArgsConstructor;
import org.example.dtos.AddressRequest;
import org.example.repositories.AddressRepository;
import org.example.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.example.utils.validation.ValidatorUtils.checkFalse;
import static org.example.utils.validation.ValidatorUtils.checkTrue;

@Service
@RequiredArgsConstructor
public class ValidationAddressService {

    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;

    public void validateAddressForCreate(final AddressRequest addressRequest) {
        checkFalse(addressRepository.existsByAddress(addressRequest.getAddress()), "This address already exists");
    }

    public void validateAddressForUpdate(final UUID addressId, final AddressRequest addressRequest) {
        checkTrue(addressRepository.existsById(addressId), "This address does not exist");
        checkFalse(addressRepository.existsByAddress(addressRequest.getAddress()), "This address already exists");
    }

    public void validateAddressForDelete(final UUID addressId) {
        checkFalse(orderRepository.existsByAddressId(addressId), "Orders with address id " + addressId + " already exist");
    }
}
