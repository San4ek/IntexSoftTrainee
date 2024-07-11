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

/**
 * Class for validating brand service operations
 */
@Service
@RequiredArgsConstructor
public class ValidationBrandService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;

    /**
     * Validates a brand creation request.
     *
     * @param brandRequest The request containing details of the brand to be created.
     */
    @Transactional(readOnly = true)
    public void validateBrandRequestForCreate(final BrandRequest brandRequest) {
        checkFalse(brandRepository.existsByName(brandRequest.getName()), "Brand is already exists with name: " + brandRequest.getName());
    }

    /**
     * Validates a brand update request.
     *
     * @param brandId The ID of the brand being updated.
     * @param brandRequest The updated details of the brand.
     */
    @Transactional(readOnly = true)
    public void validateBrandRequestForUpdate(final UUID brandId, final BrandRequest brandRequest) {
        checkTrue(brandRepository.existsById(brandId), "Brand doesn't exist");
        checkFalse(brandRepository.existsByName(brandRequest.getName()), "Brand is already exists with name: " + brandRequest.getName());
    }

    /**
     * Validates a brand deletion request.
     *
     * @param brandId The ID of the brand to be deleted.
     */
    @Transactional(readOnly = true)
    public void validateBrandRequestForDelete(final UUID brandId) {
        checkTrue(brandRepository.existsById(brandId), "Brand doesn't exist");
        checkFalse(productRepository.existsByBrandId(brandId), "Some products of this brand still exist");
    }
}
