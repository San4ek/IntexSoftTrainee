package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dtos.StockItemAmount;
import org.example.enums.StockOperationEnum;
import org.example.services.StockService;
import org.example.services.client.StockClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Service implementation for stock operations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockClient stockClient;

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
     * Change the stock amount of a specific stock item.
     *
     * @param stockItemId the ID of the stock item.
     * @param amount the amount to change the stock by.
     */
    @Override
    @Transactional
    public void changeStockAmount(final UUID stockItemId, final Long amount, final StockOperationEnum operation) {
        log.info("Changing stock item amount by {} with id {} ", amount, stockItemId);
        stockClient.changeStockAmount(stockItemId, amount, operation);
    }
}
