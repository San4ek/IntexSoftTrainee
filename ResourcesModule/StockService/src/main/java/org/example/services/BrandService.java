package org.example.services;

import org.example.dtos.BrandRequest;
import org.example.entities.BrandEntity;

import java.util.UUID;

/**
 * Interface with business logic for brands
 */
public interface BrandService {

    BrandEntity getBrandById(UUID id);

    BrandEntity createBrand(BrandRequest brandRequest);

    BrandEntity updateBrand(UUID brandId, BrandRequest brandRequest);

    void deleteBrand(UUID brandId);
}
