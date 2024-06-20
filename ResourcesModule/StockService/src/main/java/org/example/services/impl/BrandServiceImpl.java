package org.example.services.impl;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.example.dtos.BrandRequest;
import org.example.entities.BrandEntity;
import org.example.mappers.BrandMapper;
import org.example.repositories.BrandRepository;
import org.example.services.BrandService;
import org.example.validation.ValidationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    private final ValidationService validationService;


    @Override
    public BrandEntity getBrandByName(final String name) {
        return brandRepository.findByBrandName(name).orElseThrow();
    }

    @Override
    public BrandEntity createBrand(final BrandRequest brandRequest) {
        validationService.validateBrandRequestForCreate(brandRequest);
        BrandEntity brandEntity = brandMapper.toEntity(brandRequest);
        return brandRepository.saveAndFlush(brandEntity);
    }

    @Override
    public BrandEntity updateBrand(final UUID brandId, final BrandRequest brandRequest) {
        validationService.validateBrandRequestForUpdate(brandId, brandRequest);
        BrandEntity existingBrandEntity = brandRepository.findById(brandId).orElseThrow();
        existingBrandEntity.setBrandName(brandRequest.getBrandName());
        return brandRepository.saveAndFlush(existingBrandEntity);
    }

    @Override
    public void deleteBrand(final UUID brandId) {
        validationService.validateBrandRequestForDelete(brandId);
        brandRepository.deleteById(brandId);
    }
}
