package org.example.controllers;

import org.example.dtos.AddressRequest;
import org.example.dtos.AddressResponse;
import org.example.exceptions.EndpointNotImplementedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Interface for controller with address operations.
 */
@RequestMapping("/api/addresses")
public interface AddressOperationsController {

    @GetMapping
    default @ResponseBody List<AddressResponse> getAddresses() throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PostMapping
    default @ResponseBody AddressResponse createAddress(@RequestBody AddressRequest addressRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PutMapping("/{addressId}")
    default @ResponseBody AddressResponse updateAddress(@PathVariable("addressId") UUID addressId, @RequestBody AddressRequest addressRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @DeleteMapping("/{addressId}")
    default void deleteAddress(@PathVariable("addressId") UUID addressId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
