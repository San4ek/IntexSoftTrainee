package org.example.validation;

import lombok.RequiredArgsConstructor;
import org.example.dtos.AddressRequest;
import org.example.repositories.AddressRepository;
import org.example.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.example.utils.validation.ValidatorUtils.checkFalse;
import static org.example.utils.validation.ValidatorUtils.checkTrue;
/**
 * Class for validating addresses operations.
 */
@Service
@RequiredArgsConstructor
public class ValidationAddressService {

    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;

    /**
     * Validates the address for create operation.
     *
     * @param addressRequest the address request containing the address to validate
     */
    public void validateAddressForCreate(final AddressRequest addressRequest) {
        checkFalse(addressRepository.existsByAddress(addressRequest.getAddress()), "This address already exists");
    }

    /**
     * Validates the address for update operation.
     *
     * @param addressId the ID of the address to validate
     * @param addressRequest the address request containing the new address to validate
     */
    public void validateAddressForUpdate(final UUID addressId, final AddressRequest addressRequest) {
        checkTrue(addressRepository.existsById(addressId), "This address does not exist");
        checkFalse(addressRepository.existsByAddress(addressRequest.getAddress()), "This address already exists");
    }

    /**
     * Validates the address for delete operation.
     *
     * @param addressId the ID of the address to validate
     */
    public void validateAddressForDelete(final UUID addressId) {
        checkTrue(addressRepository.existsById(addressId), "This address does not exist");
        checkFalse(orderRepository.existsByAddressId(addressId), "Orders with address id " + addressId + " already exist");
    }
}
