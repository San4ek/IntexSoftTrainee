package org.example.services;

import jakarta.validation.Valid;
import org.example.dtos.ProductRequest;
import org.example.entities.ProductEntity;

import java.util.UUID;

/**
 * Interface with business logic for products
 */
public interface ProductService {

    ProductEntity getProductById(UUID id);

    ProductEntity createProduct(@Valid ProductRequest productRequest);

    ProductEntity updateProduct(UUID productId, @Valid ProductRequest productRequest);

    void deleteProduct(UUID productId);
}
