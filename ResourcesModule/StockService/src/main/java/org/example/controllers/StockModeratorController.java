package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.controllers.interfaces.StockModeratorOperations;
import org.example.dtos.StockItemRequest;
import org.example.dtos.StockItemResponse;
import org.example.mappers.StockItemMapper;
import org.example.services.impl.StockModeratorServiceImpl;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class StockModeratorController implements StockModeratorOperations {

    private final StockModeratorServiceImpl stockModeratorService;
    private final StockItemMapper stockItemCreateRequestMapper;

    @Override
    public StockItemResponse getStockItemById(final UUID stockItemId) {
        return stockItemCreateRequestMapper.toDto(stockModeratorService.findById(stockItemId));
    }

    @Override
    public StockItemResponse createStockItem(final StockItemRequest stockItemRequest) {
        return stockItemCreateRequestMapper.toDto(stockModeratorService.createStockItem(stockItemRequest));
    }

    @Override
    public StockItemResponse updateStockItem(final UUID stockItemId, final StockItemRequest stockItemRequest) {
        return stockItemCreateRequestMapper.toDto(stockModeratorService.updateStockItem(stockItemId, stockItemRequest));
    }

    @Override
    public void deleteStockItem(final UUID stockItemId) {
        stockModeratorService.deleteStockItem(stockItemId);
    }
}
