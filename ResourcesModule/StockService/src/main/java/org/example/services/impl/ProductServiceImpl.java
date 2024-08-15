package org.example.services.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dtos.ProductRequest;
import org.example.entities.ProductEntity;
import org.example.mappers.ProductMapper;
import org.example.repositories.ProductRepository;
import org.example.repositories.StockRepository;
import org.example.services.ProductService;
import org.example.dtos.LoggedAccountDetailsProvider;
import org.example.validation.ValidationProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

/**
 * Service implementation for products
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ValidationProductService validationProductService;
    private final LoggedAccountDetailsProvider loggedAccountDetailsProvider;
    private final StockRepository stockRepository;

    /**
     * Finds a product by its id.
     *
     * @param id The id of the product to find.
     * @return The product entity matching the provided id.
     */
    @Override
    @Transactional(readOnly = true)
    public ProductEntity getProductById(final UUID id) {
        return productRepository.getById(id);
    }

    /**
     * Creates a new product based on the provided request.
     *
     * @param productRequest The request containing details of the product to be created.
     * @return The newly created product entity.
     */
    @Override
    @Transactional
    public ProductEntity createProduct(@Valid final ProductRequest productRequest) {
        log.info("Creating product with name {}", productRequest.getName());
        validationProductService.validateProductRequestForCreate(productRequest);
        ProductEntity productEntity = productMapper.toEntity(productRequest);
        productEntity.setCreatedBy(loggedAccountDetailsProvider.getAccountId());
        return productRepository.save(productEntity);
    }

    /**
     * Updates an existing product identified by its ID.
     *
     * @param productId      The ID of the product to update.
     * @param productRequest The request containing updated details of the product.
     * @return The updated product entity.
     */
    @Override
    @Transactional
    public ProductEntity updateProduct(final UUID productId, @Valid final ProductRequest productRequest) {
        log.info("Updating product with id: {} ", productId);
        validationProductService.validateProductRequestForUpdate(productId, productRequest);
        ProductEntity existingProductEntity = productRepository.getById(productId);
        productMapper.toEntity(existingProductEntity, productRequest);
        existingProductEntity.setEditedBy(loggedAccountDetailsProvider.getAccountId());
        return productRepository.save(existingProductEntity);
    }

    /**
     * Deletes a product identified by its ID.
     *
     * @param productId The ID of the product to delete.
     */
    @Override
    @Transactional
    public void deleteProduct(final UUID productId) {
        log.info("Deleting product with id: {}", productId);
        validationProductService.validateProductForDelete(productId);
        stockRepository.deleteByProductId(productId);
        productRepository.deleteById(productId);
    }
}
