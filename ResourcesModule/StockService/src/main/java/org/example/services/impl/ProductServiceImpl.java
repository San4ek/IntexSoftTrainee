package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dtos.ProductRequest;
import org.example.entities.BrandEntity;
import org.example.entities.ProductEntity;
import org.example.exceptions.BrandNotExistException;
import org.example.exceptions.ProductNotExistException;
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
    public ProductEntity createProduct(final ProductRequest productRequest) {
        log.info("Creating product with name {}", productRequest.getName());
        validationProductService.validateProductRequestForCreate(productRequest);
        ProductEntity productEntity = productMapper.toEntity(productRequest);
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
    public ProductEntity updateProduct(final UUID productId, final ProductRequest productRequest) {
        log.info("Updating product with id: {} ", productId);
        validationProductService.validateProductRequestForUpdate(productId, productRequest);
        ProductEntity existingProductEntity = productRepository.getById(productId);
        existingProductEntity.setName(productRequest.getName());
        existingProductEntity.setType(productRequest.getType());
        existingProductEntity.setCurrency(productRequest.getCurrency());
        existingProductEntity.setPrice(productRequest.getPrice());
        BrandEntity brandEntity = brandRepository.getById(productRequest.getBrandId());
        existingProductEntity.setBrand(brandEntity);
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
        productRepository.deleteById(productId);
    }


}
