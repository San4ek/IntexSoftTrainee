package org.example.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.example.controllers.BrandModeratorOperations;
import org.example.dtos.BrandRequest;
import org.example.dtos.BrandResponse;
import org.example.mappers.BrandMapper;
import org.example.services.impl.BrandServiceImpl;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Controller with methods for moderator to work with brand
 */
@RestController
@RequiredArgsConstructor
public class BrandModeratorController implements BrandModeratorOperations {

    private final BrandServiceImpl brandService;
    private final BrandMapper brandMapper;

    /**
     * Finds a brand by its id.
     *
     * @param id The id of the brand to find.
     * @return BrandResponse containing the found brand.
     */
    @Override
    public BrandResponse getBrandById(final UUID id) {
        return brandMapper.toDto(brandService.getBrandById(id));
    }

    /**
     * Creates a new brand based on the provided request.
     *
     * @param brandRequest The request containing details of the brand to be created.
     * @return BrandResponse containing the newly created brand.
     */
    @Override
    public BrandResponse createBrand(final BrandRequest brandRequest) {
        return brandMapper.toDto(brandService.createBrand(brandRequest));
    }

    /**
     * Updates an existing brand identified by its ID.
     *
     * @param brandId      The ID of the brand to update.
     * @param brandRequest The request containing updated details of the brand.
     * @return BrandResponse containing the updated brand.
     */
    @Override
    public BrandResponse updateBrand(final UUID brandId, final BrandRequest brandRequest) {
        return brandMapper.toDto(brandService.updateBrand(brandId, brandRequest));
    }

    /**
     * Deletes a brand identified by its ID.
     *
     * @param brandId The ID of the brand to delete.
     */
    @Override
    public void deleteBrand(final UUID brandId) {
         brandService.deleteBrand(brandId);
    }
}
