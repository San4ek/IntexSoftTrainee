package org.example.controllers;

import jakarta.validation.Valid;
import org.example.dtos.BrandRequest;
import org.example.dtos.BrandResponse;
import org.example.exceptions.EndpointNotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Interface with operations for brand moderator controller
 */
@RequestMapping("/api/brands")
public interface BrandModeratorOperations {

    @GetMapping("/{brandId}")
    @ResponseStatus(HttpStatus.OK)
    default @ResponseBody BrandResponse getBrandById(@PathVariable("brandId") UUID brandId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    default @ResponseBody BrandResponse createBrand(@Valid @RequestBody BrandRequest brandRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PutMapping("/{brandId}")
    @ResponseStatus(HttpStatus.OK)
    default @ResponseBody BrandResponse updateBrand(@PathVariable("brandId") UUID brandId, @Valid @RequestBody BrandRequest brandRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @DeleteMapping("/{brandId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    default void deleteBrand(@PathVariable("brandId") UUID brandId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
