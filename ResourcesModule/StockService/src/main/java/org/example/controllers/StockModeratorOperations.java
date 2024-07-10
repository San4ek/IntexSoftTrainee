package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import org.example.dtos.StockItemRequest;
import org.example.dtos.StockItemResponse;
import org.example.exceptions.EndpointNotImplementedException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Interface with operations for stock moderator controller
 */
@Tag(name = "ModeratorStock", description = "Moderator's stock APIs")
@RequestMapping("/stock")
public interface StockModeratorOperations {

    @SneakyThrows
    @Operation(summary = "Get product by id")
    @ApiResponse(responseCode = "501")
    @ApiResponse(responseCode = "200")
    @GetMapping("/find/{stockItemId}")
    default @ResponseBody StockItemResponse getStockItemById(@PathVariable UUID stockItemId) {
        throw new EndpointNotImplementedException();
    }

    @SneakyThrows
    @Operation(summary = "Create a new product")
    @ApiResponse(responseCode = "501")
    @ApiResponse(responseCode = "200")
    @PostMapping("/create")
    default @ResponseBody StockItemResponse createStockItem(@RequestBody StockItemRequest stockItemDto) {
        throw new EndpointNotImplementedException();
    }

    @SneakyThrows
    @Operation(summary = "Update existing product")
    @ApiResponse(responseCode = "501")
    @ApiResponse(responseCode = "200")
    @PutMapping("/update/{stockItemId}")
    default @ResponseBody StockItemResponse updateStockItem(@PathVariable UUID stockItemId, @RequestBody StockItemRequest stockItemDto) {
        throw new EndpointNotImplementedException();
    }

    @SneakyThrows
    @Operation(summary = "Delete product")
    @ApiResponse(responseCode = "501")
    @ApiResponse(responseCode = "200")
    @DeleteMapping("/delete/{stockItemId}")
    default void deleteStockItem(@PathVariable UUID stockItemId) {
        throw new EndpointNotImplementedException();
    }
}
