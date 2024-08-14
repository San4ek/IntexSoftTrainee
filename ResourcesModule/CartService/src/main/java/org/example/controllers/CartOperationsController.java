package org.example.controllers;

import org.example.dtos.*;
import org.example.exceptions.EndpointNotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Interface for controller with cart operations.
 */
@RequestMapping("/api/carts")
public interface CartOperationsController {

    @GetMapping("/{cartId}")
    @ResponseStatus(HttpStatus.OK)
    default @ResponseBody CartWithItemsResponse getCart(@PathVariable("cartId") UUID cartId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    default @ResponseBody CartResponse createCart(@RequestBody CartRequest cartRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PutMapping("/{cartId}")
    @ResponseStatus(HttpStatus.OK)
    default @ResponseBody CartResponse updateCart(@PathVariable("cartId") UUID cartId, @RequestBody CartRequest cartRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PostMapping("/item")
    @ResponseStatus(HttpStatus.CREATED)
    default @ResponseBody CartItemResponse addItemInCart(@RequestBody CartItemRequest cartItemRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @DeleteMapping("/item")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    default void deleteItemFromCart(@RequestParam("cartId") UUID cartId, @RequestParam("stockItemId") UUID stockItemId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @DeleteMapping("/items/{stockId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    default void deleteCartItemsWithStockId(@PathVariable("stockId") UUID stockId, @RequestHeader("STOCK-API-KEY") String apiKey) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
