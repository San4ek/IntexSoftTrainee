package me.inqu1sitor.controllers;

import me.inqu1sitor.dtos.BrandRequest;
import me.inqu1sitor.exceptions.EndpointNotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/brands")
public interface BrandController {

    @GetMapping("/{brandId}")
    default ResponseEntity<Object> getBrandById(@PathVariable UUID brandId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PostMapping
    default ResponseEntity<Object> createBrand(@RequestBody BrandRequest brandRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PutMapping("/{brandId}")
    default ResponseEntity<Object> updateBrand(@PathVariable UUID brandId, @RequestBody BrandRequest brandRequest) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @DeleteMapping("/{brandId}")
    default ResponseEntity<Object> deleteBrand(@PathVariable UUID brandId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
