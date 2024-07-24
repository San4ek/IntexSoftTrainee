package org.example.controllers;

import lombok.SneakyThrows;
import org.example.dtos.ProductRequest;
import org.example.dtos.ProductResponse;
import org.example.exceptions.EndpointNotImplementedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Interface with operations for product moderator controller
 */
@RequestMapping("/api/products")
public interface ProductModeratorOperations {

    @GetMapping("/{productId}")
    default ProductResponse getProductById(@PathVariable UUID productId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PostMapping
    default ProductResponse createProduct(@RequestBody ProductRequest productRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PutMapping("/{productId}")
    default ProductResponse updateProduct(@PathVariable UUID productId, @RequestBody ProductRequest productRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @DeleteMapping("{productId}")
    default void deleteProduct(@PathVariable UUID productId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
