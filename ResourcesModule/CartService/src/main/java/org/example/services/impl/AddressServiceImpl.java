package org.example.services.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dtos.AddressRequest;
import org.example.entities.AddressEntity;
import org.example.mappers.AddressMapper;
import org.example.repositories.AddressRepository;
import org.example.services.AddressService;
import org.example.validation.ValidationAddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

/**
 * Service implementation for addresses.
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final ValidationAddressService validationAddressService;

    /**
     * Retrieves all addresses.
     *
     * @return a list of all addresses.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AddressEntity> getAddresses() {
        return addressRepository.findAll();
    }

    /**
     * Creates a new address.
     *
     * @param addressRequest the AddressRequest object containing address information.
     * @return the created AddressEntity.
     */
    @Override
    @Transactional
    public AddressEntity createAddress(@Valid final AddressRequest addressRequest) {
        log.info("Creating new address");
        validationAddressService.validateAddressForCreate(addressRequest);
        return addressRepository.save(addressMapper.toEntity(addressRequest));
    }

    /**
     * Updates an existing address.
     *
     * @param addressId      the ID of the address to update.
     * @param addressRequest the AddressRequest object containing updated address information.
     * @return the updated AddressEntity.
     */
    @Override
    @Transactional
    public AddressEntity updateAddress(final UUID addressId, @Valid final AddressRequest addressRequest) {
        log.info("Updating address with id {}", addressId);
        validationAddressService.validateAddressForUpdate(addressId, addressRequest);
        AddressEntity addressEntity = addressRepository.getById(addressId);
        addressMapper.toEntity(addressEntity, addressRequest);
        return addressRepository.save(addressEntity);
    }

    /**
     * Deletes an existing address.
     *
     * @param addressId the ID of the address to delete.
     */
    @Override
    @Transactional
    public void deleteAddress(final UUID addressId) {
        log.info("Deleting address {}", addressId);
        validationAddressService.validateAddressForDelete(addressId);
        addressRepository.deleteById(addressId);
    }
}
