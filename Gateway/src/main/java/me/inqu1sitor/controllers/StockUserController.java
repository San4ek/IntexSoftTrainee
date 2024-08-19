package me.inqu1sitor.controllers;

import me.inqu1sitor.dtos.enums.ColorEnum;
import me.inqu1sitor.dtos.enums.SizeEnum;
import me.inqu1sitor.dtos.enums.StockOperationEnum;
import me.inqu1sitor.dtos.enums.TypeEnum;
import me.inqu1sitor.exceptions.EndpointNotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/user-stock")
public interface StockUserController {

    @GetMapping("/name/{name}")
    default ResponseEntity<Object> getStockItemsByName(@PathVariable("name") String name) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @GetMapping("/attributes")
    default ResponseEntity<Object> getStockItemsByAttributes(@RequestParam(value = "brand", required = false) String brand,
                                                             @RequestParam(value = "color", required = false) ColorEnum color,
                                                             @RequestParam(value = "size", required = false) SizeEnum size,
                                                             @RequestParam(value = "type", required = false) TypeEnum type,
                                                             @RequestParam(value = "minPrice", required = false) Double minPrice,
                                                             @RequestParam(value = "maxPrice", required = false) Double maxPrice) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @GetMapping("/{stockItemId}")
    default ResponseEntity<Object> checkStockItemAmount(@PathVariable("stockItemId") UUID stockItemId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PutMapping("/{stockItemId}")
    default ResponseEntity<Object> changeStockAmount(@PathVariable("stockItemId") UUID stockItemId, @RequestParam("amount") Long amount, @RequestParam("operation") StockOperationEnum operation) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
