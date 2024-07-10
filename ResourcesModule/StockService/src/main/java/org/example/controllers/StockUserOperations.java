package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import org.example.dtos.StockItemAmount;
import org.example.dtos.StockItemResponse;
import org.example.enums.ColorEnum;
import org.example.enums.SizeEnum;
import org.example.enums.TypeEnum;
import org.example.exceptions.EndpointNotImplementedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Interface with operations for user stock controller
 */
@Tag(name = "UserStock", description = "User's stock APIs")
@RequestMapping("/user-stock")
public interface StockUserOperations {

    @SneakyThrows
    @Operation(summary = "Get product by name")
    @ApiResponse(responseCode = "501")
    @ApiResponse(responseCode = "200")
    @GetMapping("/find/{name}")
    default @ResponseBody List<StockItemResponse> getStockItemsByName(@PathVariable String name) {
        throw new EndpointNotImplementedException();
    }

    @SneakyThrows
    @Operation(summary = "Get products by attributes")
    @ApiResponse(responseCode = "501")
    @ApiResponse(responseCode = "200")
    @GetMapping("/find/attributes")
    default List<StockItemResponse> getStockItemsByAttributes(@RequestParam(required = false) String brand,
                                                              @RequestParam(required = false) ColorEnum color,
                                                              @RequestParam(required = false) SizeEnum size,
                                                              @RequestParam(required = false) TypeEnum type,
                                                              @RequestParam(required = false) Float minPrice,
                                                              @RequestParam(required = false) Float maxPrice) {
        throw new EndpointNotImplementedException();
    }

    @SneakyThrows
    @GetMapping("/check/{stockItemId}")
    default StockItemAmount checkStockItemAmount(@PathVariable UUID stockItemId) {
        throw new EndpointNotImplementedException();
    }

    @SneakyThrows
    @PutMapping("/decrease/{stockItemId}")
    default void decreaseStock(@PathVariable UUID stockItemId, @RequestParam Long amount) {
        throw new EndpointNotImplementedException();
    }

    @SneakyThrows
    @PutMapping("/increase/{stockItemId}")
    default void increaseStock(@PathVariable UUID stockItemId, @RequestParam Long amount) {
        throw new EndpointNotImplementedException();
    }
}
