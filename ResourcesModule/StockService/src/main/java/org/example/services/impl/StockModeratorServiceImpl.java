package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dtos.StockItemRequest;
import org.example.entities.StockEntity;
import org.example.mappers.StockItemMapper;
import org.example.repositories.StockRepository;
import org.example.services.StockModeratorService;
import org.example.validation.ValidationStockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockModeratorServiceImpl implements StockModeratorService {

    private final StockRepository stockRepository;
    private final ValidationStockService validationStockService;
    private final StockItemMapper stockItemMapper;

    /**
     * Finds a stock item by ID.
     *
     * @param stockItemId The ID of the stock item.
     * @return The stock item matching the provided ID.
     */
    @Override
    @Transactional(readOnly = true)
    public StockEntity findById(final UUID stockItemId) {
        return stockRepository.getById(stockItemId);
    }

    /**
     * Creates a new stock item based on the provided request.
     *
     * @param stockItemRequest The request containing details of the stock item to be created.
     * @return The newly created stock item entity.
     */
    @Override
    @Transactional
    public StockEntity createStockItem(final StockItemRequest stockItemRequest) {
        log.info("Create stock item with productId: {}", stockItemRequest.getProductId());
        validationStockService.validateStockItemForCreate(stockItemRequest);
        return stockRepository.save(stockItemMapper.toEntity(stockItemRequest));
    }

    /**
     * Updates an existing stock item identified by its ID.
     *
     * @param stockItemId     The ID of the stock item to update.
     * @param stockItemRequest The request containing updated details of the stock item.
     * @return The updated stock item entity.
     */
    @Override
    @Transactional
    public StockEntity updateStockItem(final UUID stockItemId, final StockItemRequest stockItemRequest) {
        log.info("Update stock item with id: {}", stockItemId);
        validationStockService.validateStockItemForUpdate(stockItemId, stockItemRequest);
        StockEntity existingStockEntity = stockRepository.getById(stockItemId);
        stockItemMapper.toEntity(existingStockEntity, stockItemRequest);
        return stockRepository.save(existingStockEntity);
    }

    /**
     * Deletes a stock item identified by its ID.
     *
     * @param stockItemId The ID of the stock item to delete.
     */
    @Override
    @Transactional
    public void deleteStockItem(final UUID stockItemId) {
        log.info("Delete stock item: {}", stockItemId);
        validationStockService.validateStockItemForDelete(stockItemId);
        stockRepository.deleteById(stockItemId);
    }
}
