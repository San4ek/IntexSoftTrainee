package me.inqu1sitor.clients;

import me.inqu1sitor.dtos.BrandRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "brand-client", url = "http://localhost:8083/api/brands")
public interface BrandClient {

    @GetMapping("/{brandId}")
    ResponseEntity<Object> getBrandById(@PathVariable("brandId") UUID brandId);

    @PostMapping
    ResponseEntity<Object> createBrand(@RequestBody BrandRequest brandRequest);

    @PutMapping("/{brandId}")
    ResponseEntity<Object> updateBrand(@PathVariable("brandId") UUID brandId, @RequestBody BrandRequest brandRequest);

    @DeleteMapping("/{brandId}")
    ResponseEntity<Object> deleteBrand(@PathVariable("brandId") UUID brandId);
}
