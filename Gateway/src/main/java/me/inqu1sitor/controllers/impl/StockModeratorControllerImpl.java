package me.inqu1sitor.controllers.impl;

import lombok.RequiredArgsConstructor;
import me.inqu1sitor.clients.StockModeratorClient;
import me.inqu1sitor.controllers.StockModeratorController;
import me.inqu1sitor.dtos.StockItemRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class StockModeratorControllerImpl implements StockModeratorController {

    private final StockModeratorClient stockModeratorClient;

    @Override
    public ResponseEntity<Object> getStockItemById(final UUID stockItemId) {
        return stockModeratorClient.getStockItemById(stockItemId);
    }

    @Override
    public ResponseEntity<Object> createStockItem(final StockItemRequest stockItemDto) {
        return stockModeratorClient.createStockItem(stockItemDto);
    }

    @Override
    public ResponseEntity<Object> updateStockItem(final UUID stockItemId, final StockItemRequest stockItemDto) {
        return stockModeratorClient.updateStockItem(stockItemId, stockItemDto);
    }

    @Override
    public ResponseEntity<Object> removeStockItems(final UUID stockItemId) {
        return stockModeratorClient.removeStockItems(stockItemId);
    }

    @Override
    public ResponseEntity<Object> deleteStockItem(final UUID stockItemId) {
        return stockModeratorClient.deleteStockItem(stockItemId);
    }
}
