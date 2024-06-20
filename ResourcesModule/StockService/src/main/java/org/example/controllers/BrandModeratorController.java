package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.controllers.interfaces.BrandModeratorOperations;
import org.example.dtos.BrandRequest;
import org.example.dtos.BrandResponse;
import org.example.mappers.BrandMapper;
import org.example.services.impl.BrandServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BrandModeratorController implements BrandModeratorOperations {

    private final BrandServiceImpl brandService;
    private final BrandMapper brandMapper;

    @Override
    public BrandResponse getBrandByName(String name) {
        return brandMapper.toDto(brandService.getBrandByName(name));
    }

    @Override
    public BrandResponse createBrand(BrandRequest brandRequest) {
        return brandMapper.toDto(brandService.createBrand(brandRequest));
    }

    @Override
    public BrandResponse updateBrand(UUID brandId, BrandRequest brandRequest) {
        return brandMapper.toDto(brandService.updateBrand(brandId, brandRequest));
    }

    @Override
    public void deleteBrand(UUID brandId) {
         brandService.deleteBrand(brandId);
    }
}
