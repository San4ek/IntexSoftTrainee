package org.example.controllers;

import org.example.dtos.BrandRequest;
import org.example.dtos.BrandResponse;
import org.example.exceptions.EndpointNotImplementedException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Interface with operations for brand moderator controller
 */
@RequestMapping("/api/brands")
public interface BrandModeratorOperations {

    @GetMapping("/{brandId}")
    default BrandResponse getBrandById(@PathVariable UUID brandId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PostMapping
    default BrandResponse createBrand(@RequestBody BrandRequest brandRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PutMapping("/{brandId}")
    default BrandResponse updateBrand(@PathVariable UUID brandId, @RequestBody BrandRequest brandRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @DeleteMapping("/{brandId}")
    default void deleteBrand(@PathVariable UUID brandId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
