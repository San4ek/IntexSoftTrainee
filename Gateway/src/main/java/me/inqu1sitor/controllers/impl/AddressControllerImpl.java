package me.inqu1sitor.controllers.impl;

import lombok.RequiredArgsConstructor;
import me.inqu1sitor.clients.AddressClient;
import me.inqu1sitor.controllers.AddressController;
import me.inqu1sitor.dtos.AddressRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AddressControllerImpl implements AddressController {

    private final AddressClient addressClient;

    @Override
    public ResponseEntity<Object> getAddresses() {
        return addressClient.getAddresses();
    }

    @Override
    public ResponseEntity<Object> createAddress(final AddressRequest addressRequest) {
        return addressClient.createAddress(addressRequest);
    }

    @Override
    public ResponseEntity<Object> updateAddress(final UUID addressId, final AddressRequest addressRequest) {
        return addressClient.updateAddress(addressId, addressRequest);
    }

    @Override
    public ResponseEntity<Object> deleteAddress(final UUID addressId) {
        return addressClient.deleteAddress(addressId);
    }
}
