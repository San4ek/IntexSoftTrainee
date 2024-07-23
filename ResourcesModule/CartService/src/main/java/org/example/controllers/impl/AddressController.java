package org.example.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.example.controllers.AddressOperationsController;
import org.example.dtos.AddressRequest;
import org.example.dtos.AddressResponse;
import org.example.mappers.AddressMapper;
import org.example.services.impl.AddressServiceImpl;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing addresses.
 */
@RestController
@RequiredArgsConstructor
public class AddressController implements AddressOperationsController {

    private final AddressServiceImpl addressService;
    private final AddressMapper addressMapper;

    /**
     * Retrieve all addresses.
     *
     * @return a list of {@link AddressResponse} containing all addresses.
     */
    @Override
    public List<AddressResponse> getAddresses() {
        return addressMapper.toDto(addressService.getAddresses());
    }

    /**
     * Create a new address.
     *
     * @param addressRequest the request object containing the details of the address to create.
     * @return the created {@link AddressResponse}.
     */
    @Override
    public AddressResponse createAddress(final AddressRequest addressRequest) {
        return addressMapper.toDto(addressService.createAddress(addressRequest));
    }

    /**
     * Update an existing address.
     *
     * @param addressId the id of the address to update.
     * @param addressRequest the request object containing the updated details of the address.
     * @return the updated {@link AddressResponse}.
     */
    @Override
    public AddressResponse updateAddress(final UUID addressId, final AddressRequest addressRequest) {
        return addressMapper.toDto(addressService.updateAddress(addressId, addressRequest));
    }

    /**
     * Delete an address by its id.
     *
     * @param addressId the id of the address to delete.
     */
    @Override
    public void deleteAddress(final UUID addressId) {
        addressService.deleteAddress(addressId);
    }
}
