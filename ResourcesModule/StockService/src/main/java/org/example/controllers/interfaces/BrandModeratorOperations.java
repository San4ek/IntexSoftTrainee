package org.example.controllers.interfaces;

import lombok.SneakyThrows;
import org.example.dtos.BrandRequest;
import org.example.dtos.BrandResponse;
import org.example.dtos.ProductRequest;
import org.example.dtos.ProductResponse;
import org.example.exceptions.EndpointNotImplementedException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/brands")
public interface BrandModeratorOperations {

    @SneakyThrows
    @GetMapping("/find/{name}")
    default BrandResponse getBrandByName(@PathVariable String name){
        throw new EndpointNotImplementedException();
    }

    @SneakyThrows
    @PostMapping("/create")
    default BrandResponse createBrand(@RequestBody BrandRequest brandRequest){
        throw new EndpointNotImplementedException();
    }

    @SneakyThrows
    @PutMapping("/update/{brandId}")
    default BrandResponse updateBrand(@PathVariable UUID brandId, @RequestBody BrandRequest brandRequest){
        throw new EndpointNotImplementedException();
    }

    @SneakyThrows
    @DeleteMapping("/delete/{brandId}")
    default void deleteBrand(@PathVariable UUID brandId){
        throw new EndpointNotImplementedException();
    }
}
