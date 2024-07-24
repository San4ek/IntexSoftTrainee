package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import org.example.dtos.StockItemAmount;
import org.example.dtos.StockItemResponse;
import org.example.enums.ColorEnum;
import org.example.enums.SizeEnum;
import org.example.enums.StockOperationEnum;
import org.example.enums.TypeEnum;
import org.example.exceptions.EndpointNotImplementedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Interface with operations for user stock controller
 */
@Tag(name = "UserStock", description = "User's stock APIs")
@RequestMapping("/api/user-stock")
public interface StockUserOperations {

    @Operation(summary = "Get product by name")
    @ApiResponse(responseCode = "501")
    @ApiResponse(responseCode = "200")
    @GetMapping("/name/{name}")
    default @ResponseBody List<StockItemResponse> getStockItemsByName(@PathVariable String name) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @Operation(summary = "Get products by attributes")
    @ApiResponse(responseCode = "501")
    @ApiResponse(responseCode = "200")
    @GetMapping("/attributes")
    default List<StockItemResponse> getStockItemsByAttributes(@RequestParam(required = false) String brand,
                                                              @RequestParam(required = false) ColorEnum color,
                                                              @RequestParam(required = false) SizeEnum size,
                                                              @RequestParam(required = false) TypeEnum type,
                                                              @RequestParam(required = false) Float minPrice,
                                                              @RequestParam(required = false) Float maxPrice) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @GetMapping("/{stockItemId}")
    default StockItemAmount checkStockItemAmount(@PathVariable UUID stockItemId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PutMapping("/{stockItemId}")
    default void changeStockAmount(@PathVariable UUID stockItemId, @RequestParam Long amount, @RequestParam StockOperationEnum operation) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
