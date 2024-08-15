package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.dtos.PagedStockItemsResponse;
import org.example.dtos.StockItemAmount;
import org.example.dtos.StockItemResponse;
import org.example.enums.ColorEnum;
import org.example.enums.SizeEnum;
import org.example.enums.StockOperationEnum;
import org.example.enums.TypeEnum;
import org.example.exceptions.EndpointNotImplementedException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Interface with operations for user stock controller
 */
@Tag(name = "UserStock", description = "User's stock APIs")
@RequestMapping("/api/user-stock")
public interface StockUserOperations {

    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    default @ResponseBody PagedStockItemsResponse getStockItemsByName(@PathVariable("name") String name,
                                                                      @RequestParam(defaultValue = "0") Integer page,
                                                                      @RequestParam(defaultValue = "3") Integer pageSize)  throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @GetMapping("/attributes")
    @ResponseStatus(HttpStatus.OK)
    default @ResponseBody PagedStockItemsResponse getStockItemsByAttributes(@RequestParam(value = "brand", required = false) String brand,
                                                                            @RequestParam(value = "color", required = false) ColorEnum color,
                                                                            @RequestParam(value = "size", required = false) SizeEnum size,
                                                                            @RequestParam(value = "type", required = false) TypeEnum type,
                                                                            @RequestParam(value = "minPrice", required = false) Double minPrice,
                                                                            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                                                                            @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                            @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @GetMapping("/{stockItemId}")
    @ResponseStatus(HttpStatus.OK)
    default @ResponseBody StockItemAmount checkStockItemAmount(@PathVariable("stockItemId") UUID stockItemId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PutMapping("/{stockItemId}")
    @ResponseStatus(HttpStatus.OK)
    default void changeStockAmount(@PathVariable("stockItemId") UUID stockItemId, @RequestParam("amount") Long amount, @RequestParam("operation") StockOperationEnum operation, @RequestHeader("CART-API-KEY") String apiKey) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
