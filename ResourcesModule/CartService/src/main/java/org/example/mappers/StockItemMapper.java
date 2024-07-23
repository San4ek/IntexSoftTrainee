package org.example.mappers;

import org.example.dtos.StockItemAmount;
import org.example.entities.CartItemEntity;
import org.example.services.impl.StockServiceImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class StockItemMapper {

    @Autowired
    private StockServiceImpl stockService;

    @Mapping(target = "stockItemId", source = "stockId")
    @Mapping(target = "price", source = "stockId", qualifiedByName = "mapPrice")
    public abstract StockItemAmount toDto(final CartItemEntity cartItemEntity);

    @Named("mapPrice")
    public Double mapPrice(final UUID stockId) {
        StockItemAmount stockItemAmount = stockService.getStockItemById(stockId);
        return stockItemAmount.getPrice();
    }
}
