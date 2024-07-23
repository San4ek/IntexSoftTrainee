package org.example.controllers;

import lombok.SneakyThrows;
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

    @GetMapping("/get/{orderId}")
    default @ResponseBody OrderResponse getOrder(@PathVariable UUID orderId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PostMapping("/create")
    default @ResponseBody OrderResponse createOrder(@RequestBody OrderRequest orderRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @DeleteMapping("/delete/{addressId}")
    default void deleteOrdersWithAddress(@PathVariable UUID addressId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
