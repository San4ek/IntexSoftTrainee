package me.inqu1sitor.controllers.impl;

import lombok.RequiredArgsConstructor;
import me.inqu1sitor.clients.BrandClient;
import me.inqu1sitor.controllers.BrandController;
import me.inqu1sitor.dtos.BrandRequest;
import me.inqu1sitor.exceptions.EndpointNotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BrandControllerImpl implements BrandController {

    private final BrandClient brandClient;

    @Override
    public ResponseEntity<Object> getBrandById(final UUID brandId) {
        return brandClient.getBrandById(brandId);
    }

    @Override
    public ResponseEntity<Object> createBrand(final BrandRequest brandRequest) {
        return brandClient.createBrand(brandRequest);
    }

    @Override
    public ResponseEntity<Object> updateBrand(final UUID brandId, final BrandRequest brandRequest) {
        return brandClient.updateBrand(brandId, brandRequest);
    }

    @Override
    public ResponseEntity<Object> deleteBrand(final UUID brandId) {
        return brandClient.deleteBrand(brandId);
    }
}
