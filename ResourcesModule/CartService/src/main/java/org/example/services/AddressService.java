package org.example.services;

import jakarta.validation.Valid;
import org.example.dtos.AddressRequest;
import org.example.entities.AddressEntity;

import java.util.List;
import java.util.UUID;

/**
 * Interface for addresses
 */
public interface AddressService {

    List<AddressEntity> getAddresses();

    AddressEntity createAddress(@Valid AddressRequest addressRequest);

    AddressEntity updateAddress(UUID addressId, @Valid AddressRequest addressRequest);

    void deleteAddress(UUID addressId);
}
