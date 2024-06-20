package org.example.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.example.dtos.BrandRequest;
import org.example.dtos.ProductRequest;
import org.example.dtos.StockItemRequest;
import org.example.entities.BrandEntity;
import org.example.entities.ProductEntity;
import org.example.mappers.StockItemMapper;
import org.example.repositories.BrandRepository;
import org.example.repositories.ProductRepository;
import org.example.repositories.StockRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

import static org.example.utils.validation.ValidatorUtils.checkFalse;
import static org.example.utils.validation.ValidatorUtils.checkTrue;

@Service
@RequiredArgsConstructor
public class ValidationService {

    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private final Validator validator;
    private final BrandRepository brandRepository;

    // STOCK
    public void validateStockItemForCreate(final StockItemRequest stockItemRequest) {
        Set<ConstraintViolation<StockItemRequest>> violations = validator.validate(stockItemRequest);
        checkTrue(violations.isEmpty(), "StockItemRequest is not valid");
        ProductEntity productEntity = productRepository.findById(stockItemRequest.getProductId()).orElseThrow();
        checkFalse(stockRepository.existsByColorAndSizeAndProduct(stockItemRequest.getColor(), stockItemRequest.getSize(), productEntity));
    }

    public void validateStockItemForUpdate(final UUID stockItemId, final StockItemRequest stockItemRequest) {
        Set<ConstraintViolation<StockItemRequest>> violations = validator.validate(stockItemRequest);
        checkTrue(violations.isEmpty(), "StockItemRequest is not valid");
        checkTrue(stockRepository.existsById(stockItemId), "StockItemId does not exist");
        ProductEntity productEntity = productRepository.findById(stockItemRequest.getProductId()).orElseThrow();
        checkFalse(stockRepository.existsByColorAndSizeAndProduct(stockItemRequest.getColor(), stockItemRequest.getSize(), productEntity));
    }

    public void validateStockItemForDelete(final UUID stockItemId) {
        checkTrue(stockRepository.existsByIdAndAmountEquals(stockItemId, 0), "Stock item must exist and amount must be 0");
    }

    // PRODUCT
    public void validateProductRequestForCreate(final ProductRequest productRequest) {
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        checkTrue(violations.isEmpty(), "ProductRequest is not valid");
        BrandEntity brandEntity = brandRepository.findById(productRequest.getBrandId()).orElseThrow();
        checkFalse(productRepository.existsByNameAndTypeAndBrand(productRequest.getName(), productRequest.getType(), brandEntity), "Product with such parameters is already exists");
    }

    public void validateProductRequestForUpdate(final UUID productId, final ProductRequest productRequest) {
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        checkTrue(violations.isEmpty(), "ProductRequest is not valid");
        checkTrue(productRepository.existsById(productId), "Product does not exist");
        BrandEntity brandEntity = brandRepository.findById(productRequest.getBrandId()).orElseThrow();
        checkFalse(productRepository.existsByNameAndTypeAndBrand(productRequest.getName(), productRequest.getType(), brandEntity), "Product with such parameters is already exists");
    }

    public void validateProductForDelete(final UUID productId) {
        checkTrue(productRepository.existsById(productId), "Product does not exist");
        checkFalse(stockRepository.existsByProductIdAndAmountGreaterThan(productId, 0), "Product amount must be 0");
    }

    // BRAND
    public void validateBrandRequestForCreate(final BrandRequest brandRequest) {
        Set<ConstraintViolation<BrandRequest>> violations = validator.validate(brandRequest);
        checkTrue(violations.isEmpty(), "BrandRequest is not valid");
        checkFalse(brandRepository.existsByBrandName(brandRequest.getBrandName()));
    }

    public void validateBrandRequestForUpdate(final UUID brandId, final BrandRequest brandRequest) {
        Set<ConstraintViolation<BrandRequest>> violations = validator.validate(brandRequest);
        checkTrue(violations.isEmpty(), "BrandRequest is not valid");
        checkTrue(brandRepository.existsById(brandId), "Brand does not exist");
        checkFalse(brandRepository.existsByBrandName(brandRequest.getBrandName()));
    }

    public void validateBrandRequestForDelete(final UUID brandId) {
        checkTrue(brandRepository.existsById(brandId), "Brand does not exist");
        checkFalse(productRepository.existsByBrandId(brandId), "Some products of this brand still exist");
    }


}
