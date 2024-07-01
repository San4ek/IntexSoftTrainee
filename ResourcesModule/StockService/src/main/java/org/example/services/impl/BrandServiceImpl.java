package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dtos.BrandRequest;
import org.example.entities.BrandEntity;
import org.example.exceptions.BrandNotExistException;
import org.example.mappers.BrandMapper;
import org.example.repositories.BrandRepository;
import org.example.services.BrandService;
import org.example.validation.ValidationBrandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    private final ValidationBrandService validationBrandService;


    /**
     * Finds a brand by its name.
     *
     * @param name The name of the brand to find.
     * @return The brand entity matching the provided name.
     * @throws BrandNotExistException if no brand with the given name exists.
     */
    @Override
    @Transactional(readOnly = true)
    public BrandEntity getBrandByName(final String name) {
        return brandRepository.findByName(name)
                .orElseThrow(() -> new BrandNotExistException("Brand doesn't exist with name: " + name));
    }

    /**
     * Creates a new brand based on the provided request.
     *
     * @param brandRequest The request containing details of the brand to be created.
     * @return The newly created brand entity.
     */
    @Override
    @Transactional
    public BrandEntity createBrand(final BrandRequest brandRequest) {
        log.info("Creating brand: {}", brandRequest);
        validationBrandService.validateBrandRequestForCreate(brandRequest);
        BrandEntity brandEntity = brandMapper.toEntity(brandRequest);
        return brandRepository.saveAndFlush(brandEntity);
    }

    /**
     * Updates an existing brand identified by its ID.
     *
     * @param brandId      The ID of the brand to update.
     * @param brandRequest The request containing updated details of the brand.
     * @return The updated brand entity.
     * @throws BrandNotExistException if no brand with the given ID exists.
     */
    @Override
    @Transactional
    public BrandEntity updateBrand(final UUID brandId, final BrandRequest brandRequest) {
        log.info("Updating brand with id: {}", brandId);
        validationBrandService.validateBrandRequestForUpdate(brandId, brandRequest);
        BrandEntity existingBrandEntity = brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotExistException("Brand doesn't exist with id: " + brandId));
        existingBrandEntity.setName(brandRequest.getName());
        return brandRepository.saveAndFlush(existingBrandEntity);
    }

    /**
     * Deletes a brand identified by its ID.
     *
     * @param brandId The ID of the brand to delete.
     */
    @Override
    @Transactional
    public void deleteBrand(final UUID brandId) {
        log.info("Deleting brand with id: {}", brandId);
        validationBrandService.validateBrandRequestForDelete(brandId);
        brandRepository.deleteById(brandId);
    }
}
