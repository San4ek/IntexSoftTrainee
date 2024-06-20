package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.controllers.interfaces.ProductModeratorOperations;
import org.example.dtos.ProductRequest;
import org.example.dtos.ProductResponse;
import org.example.mappers.ProductMapper;
import org.example.repositories.ProductRepository;
import org.example.services.ProductService;
import org.example.services.impl.ProductServiceImpl;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProductModeratorController implements ProductModeratorOperations {

    private final ProductServiceImpl productService;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse getProductByName(String name) {
        return productMapper.toDto(productService.getProductByName(name));
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        return productMapper.toDto(productService.createProduct(productRequest));
    }

    @Override
    public ProductResponse updateProduct(UUID productId, ProductRequest productRequest) {
        return productMapper.toDto(productService.updateProduct(productId, productRequest));
    }

    @Override
    public void deleteProduct(UUID productId) {
        productService.deleteProduct(productId);
    }
}
