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
@RequestMapping("/api/address")
public interface AddressOperationsController {

    @GetMapping("/get/all")
    default @ResponseBody List<AddressResponse> getAddresses() throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PostMapping("/create")
    default @ResponseBody AddressResponse createAddress(@RequestBody AddressRequest addressRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PutMapping("/update/{addressId}")
    default @ResponseBody AddressResponse updateAddress(@PathVariable UUID addressId, @RequestBody AddressRequest addressRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @DeleteMapping("/delete/{addressId}")
    default void deleteAddress(@PathVariable UUID addressId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
