package me.inqu1sitor.controllers.impl;

import lombok.RequiredArgsConstructor;
import me.inqu1sitor.clients.StockUserClient;
import me.inqu1sitor.controllers.StockUserController;
import me.inqu1sitor.dtos.enums.ColorEnum;
import me.inqu1sitor.dtos.enums.SizeEnum;
import me.inqu1sitor.dtos.enums.StockOperationEnum;
import me.inqu1sitor.dtos.enums.TypeEnum;
import me.inqu1sitor.exceptions.EndpointNotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class StockUserControllerImpl implements StockUserController {

    private final StockUserClient stockUserClient;

    @Override
    public ResponseEntity<Object> getStockItemsByName(final String name) {
        return stockUserClient.getStockItemsByName(name);
    }

    @Override
    public ResponseEntity<Object> getStockItemsByAttributes(final String brand,
                                                            final ColorEnum color,
                                                            final SizeEnum size,
                                                            final TypeEnum type,
                                                            final Double minPrice,
                                                            final Double maxPrice) {
        return stockUserClient.getStockItemsByAttributes(brand, color, size, type, minPrice, maxPrice);
    }

    @Override
    public ResponseEntity<Object> checkStockItemAmount(final UUID stockItemId) {
        return stockUserClient.checkStockItemAmount(stockItemId);
    }

    @Override
    public ResponseEntity<Object> changeStockAmount(final UUID stockItemId, final Long amount, final StockOperationEnum operation) {
        return stockUserClient.changeStockAmount(stockItemId, amount, operation);
    }
}
