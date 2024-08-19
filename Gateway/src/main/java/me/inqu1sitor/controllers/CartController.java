package me.inqu1sitor.controllers;

import me.inqu1sitor.dtos.CartItemRequest;
import me.inqu1sitor.dtos.CartRequest;
import me.inqu1sitor.exceptions.EndpointNotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/carts")
public interface CartController {

    @GetMapping("/{cartId}")
    default ResponseEntity<Object> getCart(@PathVariable("cartId") UUID cartId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PostMapping
    default ResponseEntity<Object> createCart(@RequestBody CartRequest cartRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PutMapping("/{cartId}")
    default ResponseEntity<Object> updateCart(@PathVariable("cartId") UUID cartId, @RequestBody CartRequest cartRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PostMapping("/item")
    default ResponseEntity<Object> addItemInCart(@RequestBody CartItemRequest cartItemRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @DeleteMapping("/item")
    default ResponseEntity<Object> deleteItemFromCart(@RequestParam("cartId") UUID cartId, @RequestParam("stockItemId") UUID stockItemId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
