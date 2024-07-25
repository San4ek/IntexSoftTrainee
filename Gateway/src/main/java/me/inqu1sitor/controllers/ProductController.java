package me.inqu1sitor.controllers;

import me.inqu1sitor.dtos.ProductRequest;
import me.inqu1sitor.exceptions.EndpointNotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/products")
public interface ProductController {

    @GetMapping("/{productId}")
    default ResponseEntity<Object> getProductById(@PathVariable UUID productId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PostMapping
    default ResponseEntity<Object> createProduct(@RequestBody ProductRequest productRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PutMapping("/{productId}")
    default ResponseEntity<Object> updateProduct(@PathVariable UUID productId, @RequestBody ProductRequest productRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @DeleteMapping("/{productId}")
    default ResponseEntity<Object> deleteProduct(@PathVariable UUID productId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
