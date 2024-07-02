package org.example.validation;

import lombok.RequiredArgsConstructor;
import org.example.dtos.ProductRequest;
import org.example.entities.BrandEntity;
import org.example.exceptions.BrandNotExistException;
import org.example.repositories.BrandRepository;
import org.example.repositories.ProductRepository;
import org.example.repositories.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.example.utils.validation.ValidatorUtils.checkFalse;
import static org.example.utils.validation.ValidatorUtils.checkTrue;

@Service
@RequiredArgsConstructor
public class ValidationProductService {

    private final BrandRepository brandRepository;
    private final StockRepository stockRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public void validateProductRequestForCreate(final ProductRequest productRequest) {
        final BrandEntity brandEntity = brandRepository.getById(productRequest.getBrandId());
        checkFalse(productRepository.existsByNameAndTypeAndBrand(productRequest.getName(), productRequest.getType(), brandEntity), "Product with such parameters is already exists");
    }

    @Transactional(readOnly = true)
    public void validateProductRequestForUpdate(final UUID productId, final ProductRequest productRequest) {
        checkTrue(productRepository.existsById(productId), "Product doesn't exist with id: " + productId);
        final BrandEntity brandEntity = brandRepository.getById(productRequest.getBrandId());
        checkFalse(productRepository.existsByNameAndTypeAndBrandAndPriceAndCurrency(productRequest.getName(), productRequest.getType(), brandEntity, productRequest.getPrice(), productRequest.getCurrency()), "Product with such parameters is already exists");
    }

    @Transactional(readOnly = true)
    public void validateProductForDelete(final UUID productId) {
        checkTrue(productRepository.existsById(productId), "Product doesn't exist with id: " + productId);
        checkFalse(stockRepository.existsByProductIdAndAmountGreaterThan(productId, 0), "Product amount must be 0");
    }
}
