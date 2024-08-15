package me.inqu1sitor.clients;

import me.inqu1sitor.dtos.ProductRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "product-client", url = "https://stock-service:8083/api/products")
public interface ProductClient {

    @GetMapping("/{productId}")
    ResponseEntity<Object> getProductById(@PathVariable("productId") UUID productId);

    @PostMapping
    ResponseEntity<Object> createProduct(@RequestBody ProductRequest productRequest);

    @PutMapping("/{productId}")
    ResponseEntity<Object> updateProduct(@PathVariable("productId") UUID productId, @RequestBody ProductRequest productRequest);

    @DeleteMapping("/{productId}")
    ResponseEntity<Object> deleteProduct(@PathVariable("productId") UUID productId);
}
