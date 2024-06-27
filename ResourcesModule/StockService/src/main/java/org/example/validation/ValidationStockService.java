package org.example.validation;

import lombok.RequiredArgsConstructor;
import org.example.dtos.StockItemRequest;
import org.example.entities.ProductEntity;
import org.example.exceptions.ProductNotExistException;
import org.example.repositories.ProductRepository;
import org.example.repositories.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.example.utils.validation.ValidatorUtils.checkFalse;
import static org.example.utils.validation.ValidatorUtils.checkTrue;

@Service
@RequiredArgsConstructor
public class ValidationStockService {

    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    @Transactional(readOnly = true)
    public void validateStockItemForCreate(final StockItemRequest stockItemRequest) {
        ProductEntity productEntity = productRepository.findById(stockItemRequest.getProductId())
                .orElseThrow(() -> new ProductNotExistException("Product doesn't exist with id: " + stockItemRequest.getProductId()));
        checkFalse(stockRepository.existsByColorAndSizeAndProduct(stockItemRequest.getColor(), stockItemRequest.getSize(), productEntity));
    }

    @Transactional(readOnly = true)
    public void validateStockItemForUpdate(final UUID stockItemId, final StockItemRequest stockItemRequest) {
        checkTrue(stockRepository.existsById(stockItemId), "Stock doesn't exist with id: " + stockItemId);
        ProductEntity productEntity = productRepository.findById(stockItemRequest.getProductId())
                .orElseThrow(() -> new ProductNotExistException("Product doesn't exist with id: " + stockItemRequest.getProductId()));
        checkFalse(stockRepository.existsByColorAndSizeAndProduct(stockItemRequest.getColor(), stockItemRequest.getSize(), productEntity));
    }

    @Transactional(readOnly = true)
    public void validateStockItemForDelete(final UUID stockItemId) {
        checkTrue(stockRepository.existsByIdAndAmountEquals(stockItemId, 0), "Stock item must exist and amount must be 0");
    }
}
