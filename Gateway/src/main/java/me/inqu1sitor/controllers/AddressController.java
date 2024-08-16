package me.inqu1sitor.controllers;

import me.inqu1sitor.dtos.AddressRequest;
import me.inqu1sitor.exceptions.EndpointNotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/addresses")
public interface AddressController {

    @GetMapping
    default ResponseEntity<Object> getAddresses() throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PostMapping
    default ResponseEntity<Object> createAddress(@RequestBody AddressRequest addressRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PutMapping("/{addressId}")
    default ResponseEntity<Object> updateAddress(@PathVariable("addressId") UUID addressId, @RequestBody AddressRequest addressRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @DeleteMapping("/{addressId}")
    default ResponseEntity<Object> deleteAddress(@PathVariable("addressId") UUID addressId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
