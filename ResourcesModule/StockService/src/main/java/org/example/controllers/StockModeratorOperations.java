package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.dtos.StockItemRequest;
import org.example.dtos.StockItemResponse;
import org.example.exceptions.EndpointNotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Interface with operations for stock moderator controller
 */
@Tag(name = "ModeratorStock", description = "Moderator's stock APIs")
@RequestMapping("/api/stock")
public interface StockModeratorOperations {

    @GetMapping("/{stockItemId}")
    @ResponseStatus(HttpStatus.OK)
    default @ResponseBody StockItemResponse getStockItemById(@PathVariable("stockItemId") UUID stockItemId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    default @ResponseBody StockItemResponse createStockItem(@RequestBody StockItemRequest stockItemDto) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PutMapping("/{stockItemId}")
    @ResponseStatus(HttpStatus.OK)
    default @ResponseBody StockItemResponse updateStockItem(@PathVariable("stockItemId") UUID stockItemId, @RequestBody StockItemRequest stockItemDto) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PutMapping("/{stockItemId}/utilization")
    @ResponseStatus(HttpStatus.OK)
    default void removeStockItems(@PathVariable("stockItemId") UUID stockItemId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @DeleteMapping("/{stockItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    default void deleteStockItem(@PathVariable("stockItemId") UUID stockItemId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
