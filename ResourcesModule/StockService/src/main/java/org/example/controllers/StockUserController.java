package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.controllers.interfaces.StockUserOperations;
import org.example.dtos.StockItemResponse;
import org.example.entities.StockEntity;
import org.example.enums.ColorEnum;
import org.example.enums.SizeEnum;
import org.example.enums.TypeEnum;
import org.example.mappers.StockItemMapper;
import org.example.services.impl.StockUserServiceImpl;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class StockUserController implements StockUserOperations {

    private final StockUserServiceImpl stockUserService;
    private final StockItemMapper stockItemMapper;

    @Override
    public List<StockItemResponse> getAllStockItems() {
        List<StockEntity> stockEntities = stockUserService.findAllStockItems();
        return stockItemMapper.toDto(stockEntities);
    }

    @Override
    public StockItemResponse getStockItemByName(String name) {
        return stockItemMapper.toDto(stockUserService.findStockItemByName(name));
    }

    @Override
    public List<StockItemResponse> getStockItemsByAttributes(UUID brand, ColorEnum color, SizeEnum size, TypeEnum type, Float minPrice, Float maxPrice) {
        List<StockEntity> stockEntities = stockUserService.findByAttributes(brand, color, size, type, minPrice, maxPrice);
        return stockItemMapper.toDto(stockEntities);
    }
}