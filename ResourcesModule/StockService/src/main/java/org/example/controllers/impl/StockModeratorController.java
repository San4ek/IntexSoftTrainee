package org.example.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.example.controllers.StockModeratorOperations;
import org.example.dtos.StockItemRequest;
import org.example.dtos.StockItemResponse;
import org.example.mappers.StockItemMapper;
import org.example.services.impl.StockModeratorServiceImpl;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Controller with methods for moderator to work with stock
 */
@RestController
@RequiredArgsConstructor
public class StockModeratorController implements StockModeratorOperations {

    private final StockModeratorServiceImpl stockModeratorService;
    private final StockItemMapper stockItemCreateRequestMapper;

    /**
     * Finds a stock item by its ID.
     *
     * @param stockItemId The ID of the stock item to find.
     * @return StockItemResponse containing the found stock item.
     */
    @Override
    public StockItemResponse getStockItemById(final UUID stockItemId) {
        return stockItemCreateRequestMapper.toDto(stockModeratorService.findById(stockItemId));
    }

    /**
     * Creates a new stock item based on the provided request.
     *
     * @param stockItemRequest The request containing details of the stock item to be created.
     * @return StockItemResponse containing the newly created stock item.
     */
    @Override
    public StockItemResponse createStockItem(final StockItemRequest stockItemRequest) {
        return stockItemCreateRequestMapper.toDto(stockModeratorService.createStockItem(stockItemRequest));
    }

    /**
     * Updates an existing stock item identified by its ID.
     *
     * @param stockItemId      The ID of the stock item to update.
     * @param stockItemRequest The request containing updated details of the stock item.
     * @return StockItemResponse containing the updated stock item.
     */
    @Override
    public StockItemResponse updateStockItem(final UUID stockItemId, final StockItemRequest stockItemRequest) {
        return stockItemCreateRequestMapper.toDto(stockModeratorService.updateStockItem(stockItemId, stockItemRequest));
    }

    /**
     * Deletes a stock item identified by its ID.
     *
     * @param stockItemId The ID of the stock item to delete.
     */
    @Override
    public void deleteStockItem(final UUID stockItemId) {
        stockModeratorService.deleteStockItem(stockItemId);
    }
}
