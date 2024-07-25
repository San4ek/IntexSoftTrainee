package me.inqu1sitor.controllers;

import me.inqu1sitor.dtos.OrderRequest;
import me.inqu1sitor.exceptions.EndpointNotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/orders")
public interface OrderController {

    @GetMapping("/{orderId}")
    default ResponseEntity<Object> getOrder(@PathVariable UUID orderId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PostMapping
    default ResponseEntity<Object> createOrder(@RequestBody OrderRequest orderRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @DeleteMapping("/{orderId}")
    default ResponseEntity<Object> deleteOrder(@PathVariable UUID orderId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @DeleteMapping("/address/{addressId}")
    default ResponseEntity<Object> deleteOrdersWithAddress(@PathVariable UUID addressId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
