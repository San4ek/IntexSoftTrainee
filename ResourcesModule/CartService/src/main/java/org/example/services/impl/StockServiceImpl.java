package org.example.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.dtos.StockItemAmount;
import org.example.services.StockClient;
import org.example.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Service implementation for stock operations.
 */
@Slf4j
@Service
public class StockServiceImpl implements StockService {

    private final StockClient stockClient;

    @Autowired
    public StockServiceImpl(StockClient stockClient) {
        this.stockClient = stockClient;
    }

    /**
     * Retrieves stock item details by its ID.
     *
     * @param stockItemId the ID of the stock item to retrieve.
     * @return the stock item amount details.
     */
    @Override
    @Transactional(readOnly = true)
    public StockItemAmount getStockItemById(final UUID stockItemId) {
        return stockClient.getStockItemById(stockItemId);
    }

    /**
     * Increases the stock amount of a specific stock item.
     *
     * @param stockItemId the ID of the stock item.
     * @param amount the amount to increase the stock by.
     */
    @Override
    @Transactional
    public void increaseStock(final UUID stockItemId, final Long amount) {
        log.info("Increasing stock item amount by {} with id {} ", amount, stockItemId);
        stockClient.increaseStock(stockItemId, amount);
    }

    /**
     * Decreases the stock amount of a specific stock item.
     *
     * @param stockItemId the ID of the stock item.
     * @param amount the amount to decrease the stock by.
     */
    @Override
    @Transactional
    public void decreaseStock(final UUID stockItemId, final Long amount) {
        log.info("Decreasing stock item amount by {} with id {}", amount, stockItemId);
        stockClient.decreaseStock(stockItemId, amount);
    }
}
