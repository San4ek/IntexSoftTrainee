package org.example.controllers.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import org.example.dtos.StockItemRequest;
import org.example.dtos.StockItemResponse;
import org.example.enums.ColorEnum;
import org.example.enums.SizeEnum;
import org.example.enums.TypeEnum;
import org.example.exceptions.EndpointNotImplementedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "UserStock", description = "User's stock APIs")
@RequestMapping("/stock")
public interface StockUserOperations {

    @SneakyThrows
    @Operation(summary = "Get all products in stock")
    @ApiResponse(responseCode = "501")
    @ApiResponse(responseCode = "200")
    @GetMapping("/find/all")
    default List<StockItemResponse> getAllStockItems() {
        throw new EndpointNotImplementedException();
    }

    @SneakyThrows
    @Operation(summary = "Get product by name")
    @ApiResponse(responseCode = "501")
    @ApiResponse(responseCode = "200")
    @GetMapping("/find/{name}")
    default @ResponseBody StockItemResponse getStockItemByName(@PathVariable String name) {
        throw new EndpointNotImplementedException();
    }

    @SneakyThrows
    @Operation(summary = "Get products by attributes")
    @ApiResponse(responseCode = "501")
    @ApiResponse(responseCode = "200")
    @GetMapping("/find/attributes")
    default List<StockItemResponse> getStockItemsByAttributes(@RequestParam(required = false) UUID brand,
                                                              @RequestParam(required = false) ColorEnum color,
                                                              @RequestParam(required = false) SizeEnum size,
                                                              @RequestParam(required = false) TypeEnum type,
                                                              @RequestParam(required = false) Float minPrice,
                                                              @RequestParam(required = false) Float maxPrice) {
        throw new EndpointNotImplementedException();
    }
}
