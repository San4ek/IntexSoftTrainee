package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dtos.ProductRequest;
import org.example.entities.BrandEntity;
import org.example.entities.ProductEntity;
import org.example.exceptions.BrandNotExistException;
import org.example.mappers.ProductMapper;
import org.example.repositories.BrandRepository;
import org.example.repositories.ProductRepository;
import org.example.services.ProductService;
import org.example.validation.ValidationProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final ProductMapper productMapper;
    private final ValidationProductService validationProductService;

    /**
     * Finds a product by its name.
     *
     * @param name The name of the product to find.
     * @return The product entity matching the provided name.
     * @throws RuntimeException if no product with the given name exists.
     */
    @Override
    @Transactional(readOnly = true)
    public ProductEntity getProductByName(String name) {
        return productRepository.findByName(name).orElseThrow();
    }

    /**
     * Creates a new product based on the provided request.
     *
     * @param productRequest The request containing details of the product to be created.
     * @return The newly created product entity.
     */
    @Override
    @Transactional
    public ProductEntity createProduct(ProductRequest productRequest) {
        log.info("Creating product with name {}", productRequest.getName());
        validationProductService.validateProductRequestForCreate(productRequest);
        ProductEntity productEntity = productMapper.toEntity(productRequest);
        return productRepository.saveAndFlush(productEntity);
    }

    /**
     * Updates an existing product identified by its ID.
     *
     * @param productId      The ID of the product to update.
     * @param productRequest The request containing updated details of the product.
     * @return The updated product entity.
     * @throws BrandNotExistException if no brand with the given brand ID exists.
     */
    @Override
    @Transactional
    public ProductEntity updateProduct(UUID productId, ProductRequest productRequest) {
        log.info("Updating product with id: {} ", productId);
        validationProductService.validateProductRequestForUpdate(productId, productRequest);
        ProductEntity existingProductEntity = productRepository.findById(productId).get();
        existingProductEntity.setName(productRequest.getName());
        existingProductEntity.setType(productRequest.getType());
        existingProductEntity.setCurrency(productRequest.getCurrency());
        existingProductEntity.setPrice(productRequest.getPrice());
        BrandEntity brandEntity = brandRepository.findById(productRequest.getBrandId())
                .orElseThrow(() -> new BrandNotExistException("Brand doesn't exist with id: " + productRequest.getBrandId()));
        existingProductEntity.setBrand(brandEntity);
        return productRepository.saveAndFlush(existingProductEntity);
    }

    /**
     * Deletes a product identified by its ID.
     *
     * @param productId The ID of the product to delete.
     */
    @Override
    @Transactional
    public void deleteProduct(UUID productId) {
        log.info("Deleting product with id: {}", productId);
        validationProductService.validateProductForDelete(productId);
        productRepository.deleteById(productId);
    }


}
