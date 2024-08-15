package org.example.controllers;

import org.example.dtos.ProductRequest;
import org.example.dtos.ProductResponse;
import org.example.exceptions.EndpointNotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Interface with operations for product moderator controller
 */
@RequestMapping("/api/products")
public interface ProductModeratorOperations {

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    default @ResponseBody ProductResponse getProductById(@PathVariable("productId") UUID productId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    default @ResponseBody ProductResponse createProduct(@RequestBody ProductRequest productRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    default @ResponseBody ProductResponse updateProduct(@PathVariable("productId") UUID productId, @RequestBody ProductRequest productRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    default void deleteProduct(@PathVariable("productId") UUID productId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
