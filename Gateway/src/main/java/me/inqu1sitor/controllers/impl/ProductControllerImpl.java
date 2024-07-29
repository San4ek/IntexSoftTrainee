package me.inqu1sitor.controllers.impl;

import lombok.RequiredArgsConstructor;
import me.inqu1sitor.clients.ProductClient;
import me.inqu1sitor.controllers.ProductController;
import me.inqu1sitor.dtos.ProductRequest;
import me.inqu1sitor.exceptions.EndpointNotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {

    private final ProductClient productClient;

    @Override
    public ResponseEntity<Object> getProductById(final UUID productId) {
        return productClient.getProductById(productId);
    }

    @Override
    public ResponseEntity<Object> createProduct(final ProductRequest productRequest) {
        return productClient.createProduct(productRequest);
    }

    @Override
    public ResponseEntity<Object> updateProduct(final UUID productId, final ProductRequest productRequest) {
        return productClient.updateProduct(productId, productRequest);
    }

    @Override
    public ResponseEntity<Object> deleteProduct(final UUID productId) {
        return productClient.deleteProduct(productId);
    }
}
