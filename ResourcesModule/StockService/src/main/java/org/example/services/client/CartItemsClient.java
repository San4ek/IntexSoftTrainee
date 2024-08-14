package org.example.services.client;

import org.example.configs.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

/**
 * Feign client for interacting with the cart service.
 */
@FeignClient(name = "cart-service", url = "cart-service:8082/api/carts", configuration = FeignConfig.class)
public interface CartItemsClient {

    @DeleteMapping("/items/{stockId}")
    void deleteCartItemsWithStockId(@PathVariable("stockId") UUID stockId);
}
