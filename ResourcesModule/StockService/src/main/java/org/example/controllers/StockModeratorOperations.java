package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.dtos.StockItemRequest;
import org.example.dtos.StockItemResponse;
import org.example.exceptions.EndpointNotImplementedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Interface with operations for stock moderator controller
 */
@Tag(name = "ModeratorStock", description = "Moderator's stock APIs")
@RequestMapping("/api/stock")
public interface StockModeratorOperations {

    @Operation(summary = "Get product by id")
    @ApiResponse(responseCode = "501")
    @ApiResponse(responseCode = "200")
    @GetMapping("/{stockItemId}")
    default @ResponseBody StockItemResponse getStockItemById(@PathVariable UUID stockItemId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @Operation(summary = "Create a new product")
    @ApiResponse(responseCode = "501")
    @ApiResponse(responseCode = "200")
    @PostMapping
    default @ResponseBody StockItemResponse createStockItem(@RequestBody StockItemRequest stockItemDto) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @Operation(summary = "Update existing product")
    @ApiResponse(responseCode = "501")
    @ApiResponse(responseCode = "200")
    @PutMapping("/{stockItemId}")
    default @ResponseBody StockItemResponse updateStockItem(@PathVariable UUID stockItemId, @RequestBody StockItemRequest stockItemDto) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @Operation(summary = "Remove items")
    @ApiResponse(responseCode = "501")
    @ApiResponse(responseCode = "200")
    @PutMapping("/{stockItemId}/utilization")
    default void removeStockItems(@PathVariable UUID stockItemId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @Operation(summary = "Delete product")
    @ApiResponse(responseCode = "501")
    @ApiResponse(responseCode = "200")
    @DeleteMapping("/{stockItemId}")
    default void deleteStockItem(@PathVariable UUID stockItemId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
