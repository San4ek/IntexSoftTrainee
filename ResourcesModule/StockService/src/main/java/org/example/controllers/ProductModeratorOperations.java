package org.example.controllers;

import lombok.SneakyThrows;
import org.example.dtos.ProductRequest;
import org.example.dtos.ProductResponse;
import org.example.exceptions.EndpointNotImplementedException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/products")
public interface ProductModeratorOperations {

    @SneakyThrows
    @GetMapping("/find/{name}")
    default ProductResponse getProductByName(@PathVariable String name){
        throw new EndpointNotImplementedException();
    }

    @SneakyThrows
    @PostMapping("/create")
    default ProductResponse createProduct(@RequestBody ProductRequest productRequest){
        throw new EndpointNotImplementedException();
    }

    @SneakyThrows
    @PutMapping("/update/{productId}")
    default ProductResponse updateProduct(@PathVariable UUID productId, @RequestBody ProductRequest productRequest){
        throw new EndpointNotImplementedException();
    }

    @SneakyThrows
    @DeleteMapping("/delete/{productId}")
    default void deleteProduct(@PathVariable UUID productId){
        throw new EndpointNotImplementedException();
    }


}
