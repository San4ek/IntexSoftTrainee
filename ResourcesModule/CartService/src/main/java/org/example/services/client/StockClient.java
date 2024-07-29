package org.example.services.client;

import org.example.dtos.StockItemAmount;
import org.example.enums.StockOperationEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

/**
 * Feign client for interacting with the stock service.
 */
@FeignClient(name = "stock-service", url = "http://localhost:8080/user-stock")
public interface StockClient {

    /**
     * Retrieves the stock item amount and price by stock item ID.
     *
     * @param stockItemId the ID of the stock item to retrieve.
     * @return the stock item amount and price.
     */
    @GetMapping("/{stockItemId}")
    StockItemAmount getStockItemById(@PathVariable("stockItemId") UUID stockItemId);

    /**
     * Increases the stock amount for a specific stock item.
     *
     * @param stockItemId the ID of the stock item to increase.
     * @param amount the amount to increase.
     */
    @PutMapping("/{stockItemId}")
    void changeStockAmount(@PathVariable("stockItemId") UUID stockItemId, @RequestParam("amount") Long amount, @RequestParam("operation") StockOperationEnum operation);
}
