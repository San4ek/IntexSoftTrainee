package me.inqu1sitor.clients;

import me.inqu1sitor.dtos.AddressRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "address-client", url = "http://localhost:8082/api/addresses")
public interface AddressClient {

    @GetMapping
    ResponseEntity<Object> getAddresses();

    @PostMapping
    ResponseEntity<Object> createAddress(@RequestBody AddressRequest addressRequest);

    @PutMapping("/{addressId}")
    ResponseEntity<Object> updateAddress(@PathVariable("addressId") UUID addressId, @RequestBody AddressRequest addressRequest);

    @DeleteMapping("/{addressId}")
    ResponseEntity<Object> deleteAddress(@PathVariable("addressId") UUID addressId);
}
