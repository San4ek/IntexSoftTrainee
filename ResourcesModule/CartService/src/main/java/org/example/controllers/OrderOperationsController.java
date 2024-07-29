package org.example.controllers;

import org.example.dtos.OrderRequest;
import org.example.dtos.OrderResponse;
import org.example.exceptions.EndpointNotImplementedException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Interface for controller with order operations.
 */
@RequestMapping("/api/orders")
public interface OrderOperationsController {

    @GetMapping("/{orderId}")
    default @ResponseBody OrderResponse getOrder(@PathVariable("orderId") UUID orderId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PostMapping
    default @ResponseBody OrderResponse createOrder(@RequestBody OrderRequest orderRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @DeleteMapping("/{orderId}")
    default void deleteOrder(@PathVariable("orderId") UUID orderId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @DeleteMapping("/address/{addressId}")
    default void deleteOrdersWithAddress(@PathVariable("addressId") UUID addressId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
