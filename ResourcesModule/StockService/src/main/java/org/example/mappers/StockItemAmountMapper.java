package org.example.mappers;

import org.example.dtos.StockItemAmount;
import org.example.entities.StockEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class StockItemAmountMapper {

    @Mapping(source = "id", target = "stockItemId")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "product.price", target = "price")
    public abstract StockItemAmount toDto(final StockEntity stockEntity);
}
