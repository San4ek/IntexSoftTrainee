package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.dtos.StockItemAmount;
import org.example.dtos.StockItemResponse;
import org.example.enums.ColorEnum;
import org.example.enums.SizeEnum;
import org.example.enums.StockOperationEnum;
import org.example.enums.TypeEnum;
import org.example.exceptions.EndpointNotImplementedException;
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
    default @ResponseBody List<StockItemResponse> getStockItemsByName(@PathVariable("name") String name) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @Operation(summary = "Get products by attributes")
    @ApiResponse(responseCode = "501")
    @ApiResponse(responseCode = "200")
    @GetMapping("/attributes")
    default @ResponseBody List<StockItemResponse> getStockItemsByAttributes(@RequestParam(value = "brand", required = false) String brand,
                                                              @RequestParam(value = "color", required = false) ColorEnum color,
                                                              @RequestParam(value = "size", required = false) SizeEnum size,
                                                              @RequestParam(value = "type", required = false) TypeEnum type,
                                                              @RequestParam(value = "minPrice", required = false) Double minPrice,
                                                              @RequestParam(value = "maxPrice", required = false) Double maxPrice) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @GetMapping("/{stockItemId}")
    default @ResponseBody StockItemAmount checkStockItemAmount(@PathVariable("stockItemId") UUID stockItemId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PutMapping("/{stockItemId}")
    default void changeStockAmount(@PathVariable("stockItemId") UUID stockItemId, @RequestParam("amount") Long amount, @RequestParam("operation") StockOperationEnum operation) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
