package me.inqu1sitor.clients;

import me.inqu1sitor.dtos.StockItemRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "stock-moderator-client", url = "https://localhost:8083/api/stock")
public interface StockModeratorClient {

    @GetMapping("/{stockItemId}")
    ResponseEntity<Object> getStockItemById(@PathVariable("stockItemId") UUID stockItemId);

    @PostMapping
    ResponseEntity<Object> createStockItem(@RequestBody StockItemRequest stockItemDto);

    @PutMapping("/{stockItemId}")
    ResponseEntity<Object> updateStockItem(@PathVariable("stockItemId") UUID stockItemId, @RequestBody StockItemRequest stockItemDto);

    @PutMapping("/{stockItemId}/utilization")
    ResponseEntity<Object> removeStockItems(@PathVariable("stockItemId") UUID stockItemId);

    @DeleteMapping("/{stockItemId}")
    ResponseEntity<Object> deleteStockItem(@PathVariable("stockItemId") UUID stockItemId);
}
