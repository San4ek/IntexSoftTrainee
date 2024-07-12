package org.example.controllers;

import lombok.SneakyThrows;
import org.example.dtos.*;
import org.example.exceptions.EndpointNotImplementedException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Interface for controller with cart operations.
 */
@RequestMapping("/cart")
public interface CartOperationsController {

    @SneakyThrows
    @GetMapping("/get/{cartId}")
    default @ResponseBody CartWithItemsResponse getCart(@PathVariable UUID cartId) {
        throw new EndpointNotImplementedException();
    }

    @SneakyThrows
    @PostMapping("/create")
    default @ResponseBody CartResponse createCart(@RequestBody CartRequest cartRequest) {
        throw new EndpointNotImplementedException();
    }

    @SneakyThrows
    @PutMapping("/update/{cartId}")
    default @ResponseBody CartUpdateResponse updateCart(@PathVariable UUID cartId, @RequestBody CartUpdateRequest cartUpdateRequest) {
        throw new EndpointNotImplementedException();
    }

    @SneakyThrows
    @PostMapping("/add")
    default void addItemInCart(@RequestBody CartItemRequest cartItemRequest) {
        throw new EndpointNotImplementedException();
    }

    @SneakyThrows
    @DeleteMapping("/delete")
    default void deleteItemFromCart(@RequestParam UUID cartId, @RequestParam UUID stockItemId) {
        throw new EndpointNotImplementedException();
    }

}
