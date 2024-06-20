package org.example.services;

import org.example.dtos.BrandRequest;
import org.example.entities.BrandEntity;

import java.util.UUID;

public interface BrandService {

    BrandEntity getBrandByName(String name);

    BrandEntity createBrand(BrandRequest brandRequest);

    BrandEntity updateBrand(UUID brandId, BrandRequest brandRequest);

    void deleteBrand(UUID brandId);
}
