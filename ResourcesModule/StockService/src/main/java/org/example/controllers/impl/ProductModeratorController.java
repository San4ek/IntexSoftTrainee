package org.example.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.example.controllers.ProductModeratorOperations;
import org.example.dtos.ProductRequest;
import org.example.dtos.ProductResponse;
import org.example.mappers.ProductMapper;
import org.example.services.impl.ProductServiceImpl;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProductModeratorController implements ProductModeratorOperations {

    private final ProductServiceImpl productService;
    private final ProductMapper productMapper;

    /**
     * Finds a product by its name.
     *
     * @param name The name of the product to find.
     * @return ProductResponse containing the found product.
     */
    @Override
    public ProductResponse getProductByName(String name) {
        return productMapper.toDto(productService.getProductByName(name));
    }

    /**
     * Creates a new product based on the provided request.
     *
     * @param productRequest The request containing details of the product to be created.
     * @return ProductResponse containing the newly created product.
     */
    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        return productMapper.toDto(productService.createProduct(productRequest));
    }

    /**
     * Updates an existing product identified by its ID.
     *
     * @param productId      The ID of the product to update.
     * @param productRequest The request containing updated details of the product.
     * @return ProductResponse containing the updated product.
     */
    @Override
    public ProductResponse updateProduct(UUID productId, ProductRequest productRequest) {
        return productMapper.toDto(productService.updateProduct(productId, productRequest));
    }

    /**
     * Deletes a product identified by its ID.
     *
     * @param productId The ID of the product to delete.
     */
    @Override
    public void deleteProduct(UUID productId) {
        productService.deleteProduct(productId);
    }
}
