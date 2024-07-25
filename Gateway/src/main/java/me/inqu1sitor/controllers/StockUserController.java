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
    default ResponseEntity<Object> getStockItemsByName(@PathVariable String name) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @GetMapping("/attributes")
    default ResponseEntity<Object> getStockItemsByAttributes(@RequestParam(required = false) String brand,
                                                              @RequestParam(required = false) ColorEnum color,
                                                              @RequestParam(required = false) SizeEnum size,
                                                              @RequestParam(required = false) TypeEnum type,
                                                              @RequestParam(required = false) Double minPrice,
                                                              @RequestParam(required = false) Double maxPrice) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @GetMapping("/{stockItemId}")
    default ResponseEntity<Object> checkStockItemAmount(@PathVariable UUID stockItemId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PutMapping("/{stockItemId}")
    default ResponseEntity<Object> changeStockAmount(@PathVariable UUID stockItemId, @RequestParam Long amount, @RequestParam StockOperationEnum operation) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
