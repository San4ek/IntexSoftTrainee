package me.inqu1sitor.controllers;

import me.inqu1sitor.dtos.StockItemRequest;
import me.inqu1sitor.exceptions.EndpointNotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/stock")
public interface StockModeratorController {

    @GetMapping("/{stockItemId}")
    default ResponseEntity<Object> getStockItemById(@PathVariable("stockItemId") UUID stockItemId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PostMapping
    default ResponseEntity<Object> createStockItem(@RequestBody StockItemRequest stockItemDto) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PutMapping("/{stockItemId}")
    default ResponseEntity<Object> updateStockItem(@PathVariable("stockItemId") UUID stockItemId, @RequestBody StockItemRequest stockItemDto) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PutMapping("/{stockItemId}/utilization")
    default ResponseEntity<Object> removeStockItems(@PathVariable("stockItemId") UUID stockItemId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @DeleteMapping("/{stockItemId}")
    default ResponseEntity<Object> deleteStockItem(@PathVariable("stockItemId") UUID stockItemId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
