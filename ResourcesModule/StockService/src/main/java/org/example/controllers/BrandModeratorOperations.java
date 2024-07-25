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
    default @ResponseBody BrandResponse getBrandById(@PathVariable("brandId") UUID brandId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PostMapping
    default @ResponseBody BrandResponse createBrand(@RequestBody BrandRequest brandRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PutMapping("/{brandId}")
    default @ResponseBody BrandResponse updateBrand(@PathVariable("brandId") UUID brandId, @RequestBody BrandRequest brandRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @DeleteMapping("/{brandId}")
    default void deleteBrand(@PathVariable("brandId") UUID brandId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
