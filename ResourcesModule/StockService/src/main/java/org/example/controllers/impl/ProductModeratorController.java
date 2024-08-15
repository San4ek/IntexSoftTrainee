package org.example.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.example.controllers.ProductModeratorOperations;
import org.example.dtos.ProductRequest;
import org.example.dtos.ProductResponse;
import org.example.mappers.ProductMapper;
import org.example.services.impl.ProductServiceImpl;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Controller with methods for moderator to work with products
 */
@RestController
@RequiredArgsConstructor
public class ProductModeratorController implements ProductModeratorOperations {

    private final ProductServiceImpl productService;
    private final ProductMapper productMapper;

    /**
     * Finds a product by its id.
     *
     * @param productId The id of the product to find.
     * @return ProductResponse containing the found product.
     */
    @Override
    public ProductResponse getProductById(final UUID productId) {
        return productMapper.toDto(productService.getProductById(productId));
    }

    /**
     * Creates a new product based on the provided request.
     *
     * @param productRequest The request containing details of the product to be created.
     * @return ProductResponse containing the newly created product.
     */
    @Override
    public ProductResponse createProduct(final ProductRequest productRequest) {
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
    public ProductResponse updateProduct(final UUID productId, final ProductRequest productRequest) {
        return productMapper.toDto(productService.updateProduct(productId, productRequest));
    }

    /**
     * Deletes a product identified by its ID.
     *
     * @param productId The ID of the product to delete.
     */
    @Override
    public void deleteProduct(final UUID productId) {
        productService.deleteProduct(productId);
    }
}
