package org.example.controllers;

import org.example.dtos.*;
import org.example.exceptions.EndpointNotImplementedException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Interface for controller with cart operations.
 */
@RequestMapping("/api/carts")
public interface CartOperationsController {

    @GetMapping("/{cartId}")
    default @ResponseBody CartWithItemsResponse getCart(@PathVariable UUID cartId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PostMapping
    default @ResponseBody CartResponse createCart(@RequestBody CartRequest cartRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PutMapping("/{cartId}")
    default @ResponseBody CartResponse updateCart(@PathVariable UUID cartId, @RequestBody CartRequest cartRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PostMapping("/item")
    default CartItemResponse addItemInCart(@RequestBody CartItemRequest cartItemRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @DeleteMapping("/item")
    default void deleteItemFromCart(@RequestParam UUID cartId, @RequestParam UUID stockItemId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
