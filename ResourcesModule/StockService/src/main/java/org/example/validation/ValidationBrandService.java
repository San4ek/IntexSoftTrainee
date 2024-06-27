package org.example.validation;

import lombok.RequiredArgsConstructor;
import org.example.dtos.BrandRequest;
import org.example.repositories.BrandRepository;
import org.example.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.example.utils.validation.ValidatorUtils.checkFalse;
import static org.example.utils.validation.ValidatorUtils.checkTrue;

@Service
@RequiredArgsConstructor
public class ValidationBrandService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;

    @Transactional(readOnly = true)
    public void validateBrandRequestForCreate(final BrandRequest brandRequest) {
        checkFalse(brandRepository.existsByName(brandRequest.getName()));
    }

    @Transactional(readOnly = true)
    public void validateBrandRequestForUpdate(final UUID brandId, final BrandRequest brandRequest) {
        checkTrue(brandRepository.existsById(brandId), "Brand doesn't exist");
        checkFalse(brandRepository.existsByName(brandRequest.getName()));
    }

    @Transactional(readOnly = true)
    public void validateBrandRequestForDelete(final UUID brandId) {
        checkTrue(brandRepository.existsById(brandId), "Brand doesn't exist");
        checkFalse(productRepository.existsByBrandId(brandId), "Some products of this brand still exist");
    }
}
