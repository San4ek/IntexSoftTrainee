package org.example.controllers;

import org.example.dtos.*;
import org.example.exceptions.EndpointNotImplementedException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Interface for controller with cart operations.
 */
@RequestMapping("/api/cart")
public interface CartOperationsController {

    @GetMapping("/get/{cartId}")
    default @ResponseBody CartWithItemsResponse getCart(@PathVariable UUID cartId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PostMapping("/create")
    default @ResponseBody CartResponse createCart(@RequestBody CartRequest cartRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PutMapping("/update/{cartId}")
    default @ResponseBody CartResponse updateCart(@PathVariable UUID cartId, @RequestBody CartRequest cartRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PostMapping("/add")
    default CartItemResponse addItemInCart(@RequestBody CartItemRequest cartItemRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @DeleteMapping("/remove")
    default void deleteItemFromCart(@RequestParam UUID cartId, @RequestParam UUID stockItemId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
