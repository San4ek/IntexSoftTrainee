package org.example.validation;

import lombok.RequiredArgsConstructor;
import org.example.dtos.StockItemRequest;
import org.example.entities.ProductEntity;
import org.example.repositories.ProductRepository;
import org.example.repositories.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.example.utils.validation.ValidatorUtils.checkFalse;
import static org.example.utils.validation.ValidatorUtils.checkTrue;

/**
 * Class for validating stock service operations
 */
@Service
@RequiredArgsConstructor
public class ValidationStockService {

    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    /**
     * Validates a stock item creation request.
     *
     * @param stockItemRequest The request containing details of the stock item to be created.
     */
    @Transactional(readOnly = true)
    public void validateStockItemForCreate(final StockItemRequest stockItemRequest) {
        final ProductEntity productEntity = productRepository.getById(stockItemRequest.getProductId());
        checkFalse(stockRepository.existsByColorAndSizeAndProduct(stockItemRequest.getColor(), stockItemRequest.getSize(), productEntity), "Stock item exists with such parameters");
    }

    /**
     * Validates a stock item update request.
     *
     * @param stockItemId The ID of the stock item to be updated.
     * @param stockItemRequest The updated details of the stock item.
     */
    @Transactional(readOnly = true)
    public void validateStockItemForUpdate(final UUID stockItemId, final StockItemRequest stockItemRequest) {
        checkTrue(stockRepository.existsById(stockItemId), "Stock doesn't exist with id: " + stockItemId);
        final ProductEntity productEntity = productRepository.getById(stockItemRequest.getProductId());
        checkFalse(stockRepository.existsByColorAndSizeAndProduct(stockItemRequest.getColor(), stockItemRequest.getSize(), productEntity), "Stock item exists with such parameters");
    }

    /**
     * Validates a stock item deletion request.
     *
     * @param stockItemId The ID of the stock item to be deleted.
     */
    @Transactional(readOnly = true)
    public void validateStockItemForDelete(final UUID stockItemId) {
        checkTrue(stockRepository.existsByIdAndAmountEquals(stockItemId, 0), "Stock item must exist and amount must be 0");
    }
}