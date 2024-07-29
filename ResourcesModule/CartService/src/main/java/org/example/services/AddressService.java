package org.example.services;

import org.example.dtos.AddressRequest;
import org.example.entities.AddressEntity;

import java.util.List;
import java.util.UUID;

/**
 * Interface for addresses
 */
public interface AddressService {

    List<AddressEntity> getAddresses();

    AddressEntity createAddress(AddressRequest addressRequest);

    AddressEntity updateAddress(UUID addressId, AddressRequest addressRequest);

    void deleteAddress(UUID addressId);
}
