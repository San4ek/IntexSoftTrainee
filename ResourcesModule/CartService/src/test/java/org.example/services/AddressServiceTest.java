package org.example.services;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import lombok.RequiredArgsConstructor;
import org.example.dtos.AddressRequest;
import org.example.entities.AddressEntity;
import org.example.exceptions.InvalidObjectException;
import org.example.exceptions.ObjectNotFoundException;
import org.example.repositories.AddressRepository;
import org.example.services.impl.AddressServiceImpl;
import org.example.testutils.AddressBuilder;
import org.example.testutils.AddressRequestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@Testcontainers
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AddressServiceTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    private final AddressServiceImpl addressService;
    private final AddressRepository addressRepository;

    private AddressEntity address;

    @BeforeEach
    void setUp() {
        address = addressRepository.save(AddressBuilder.anAddress().build());
    }

    @Test
    @WithJwt("jwt/user.json")
    @DisplayName("Should retrieve all addresses")
    void shouldRetrieveAllAddresses() {
        final List<AddressEntity> addresses = addressService.getAddresses();
        assertNotNull(addresses);
        assertFalse(addresses.isEmpty());
        assertTrue(addresses.contains(address));
    }

    @Test
    @WithJwt("jwt/user.json")
    @DisplayName("Should create a new address")
    void shouldCreateNewAddress() {
        final AddressRequest addressRequest = AddressRequestBuilder.anAddressRequest().build();
        final AddressEntity createdAddress = addressService.createAddress(addressRequest);
        assertNotNull(createdAddress);
        assertEquals(addressRequest.getAddress(), createdAddress.getAddress());
    }

    @Test
    @WithJwt("jwt/user.json")
    @DisplayName("Should update an existing address")
    void shouldUpdateExistingAddress() {
        final AddressRequest updatedRequest = AddressRequestBuilder.anAddressRequest().withAddress("Updated Street").build();
        final AddressEntity updatedAddress = addressService.updateAddress(address.getId(), updatedRequest);
        assertNotNull(updatedAddress);
        assertEquals(updatedRequest.getAddress(), updatedAddress.getAddress());
        assertEquals(address.getId(), updatedAddress.getId());
    }

    @Test
    @WithJwt("jwt/user.json")
    @DisplayName("Should throw exception when updating non-existing address")
    void shouldThrowExceptionWhenUpdatingNonExistingAddress() {
        final AddressRequest updatedRequest = AddressRequestBuilder.anAddressRequest().build();
        assertThrows(InvalidObjectException.class, () -> addressService.updateAddress(UUID.randomUUID(), updatedRequest));
    }

    @Test
    @WithJwt("jwt/user.json")
    @DisplayName("Should delete an existing address")
    void shouldDeleteExistingAddress() {
        addressService.deleteAddress(address.getId());
        assertThrows(ObjectNotFoundException.class, () -> addressRepository.getById(address.getId()));
    }

    @Test
    @WithJwt("jwt/user.json")
    @DisplayName("Should throw exception when deleting non-existing address")
    void shouldThrowExceptionWhenDeletingNonExistingAddress() {
        assertThrows(InvalidObjectException.class, () -> addressService.deleteAddress(UUID.randomUUID()));
    }
}
