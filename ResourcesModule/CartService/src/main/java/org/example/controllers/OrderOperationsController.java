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
@RequestMapping("/orders")
public interface OrderOperationsController {

    @SneakyThrows
    @GetMapping("/get/{orderId}")
    default @ResponseBody OrderResponse getOrder(@PathVariable UUID orderId) {
        throw new EndpointNotImplementedException();
    }

    @SneakyThrows
    @PostMapping("/create")
    default @ResponseBody OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {
        throw new EndpointNotImplementedException();
    }

}
