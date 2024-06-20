package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.dtos.ProductRequest;
import org.example.entities.BrandEntity;
import org.example.entities.ProductEntity;
import org.example.mappers.ProductMapper;
import org.example.repositories.BrandRepository;
import org.example.repositories.ProductRepository;
import org.example.services.ProductService;
import org.example.validation.ValidationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final ProductMapper productMapper;
    private final ValidationService validationService;

    @Override
    public ProductEntity getProductByName(String name) {
        return productRepository.findByName(name).orElseThrow();
    }

    @Override
    public ProductEntity createProduct(ProductRequest productRequest) {
        validationService.validateProductRequestForCreate(productRequest);
        ProductEntity productEntity = productMapper.toEntity(productRequest);
        return productRepository.saveAndFlush(productEntity);
    }

    @Override
    public ProductEntity updateProduct(UUID productId, ProductRequest productRequest) {
        validationService.validateProductRequestForUpdate(productId, productRequest);
        ProductEntity existingProductEntity = productRepository.findById(productId).orElseThrow();
        existingProductEntity.setName(productRequest.getName());
        existingProductEntity.setType(productRequest.getType());
        existingProductEntity.setCurrency(productRequest.getCurrency());
        existingProductEntity.setPrice(productRequest.getPrice());
        BrandEntity brandEntity = brandRepository.findById(productRequest.getBrandId()).orElseThrow();
        existingProductEntity.setBrand(brandEntity);
        return productRepository.saveAndFlush(existingProductEntity);
    }

    @Override
    public void deleteProduct(UUID productId) {
        validationService.validateProductForDelete(productId);
        productRepository.deleteById(productId);
    }


}
