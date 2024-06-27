package org.example.services;

import org.example.dtos.ProductRequest;
import org.example.entities.ProductEntity;

import java.util.UUID;

public interface ProductService {

    ProductEntity getProductByName(String name);

    ProductEntity createProduct(ProductRequest productRequest);

    ProductEntity updateProduct(UUID productId, ProductRequest productRequest);

    void deleteProduct(UUID productId);
}
