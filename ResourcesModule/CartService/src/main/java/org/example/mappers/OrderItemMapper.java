package org.example.mappers;

import org.example.dtos.OrderItemResponse;
import org.example.dtos.StockItemAmount;
import org.example.entities.CartItemEntity;
import org.example.entities.OrderItemEntity;
import org.example.services.impl.StockServiceImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class OrderItemMapper {

    @Autowired
    private StockServiceImpl stockServiceImpl;

    @Mapping(target = "stockId", source = "stockId")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "price", source = "price")
    public abstract OrderItemResponse toDto(final OrderItemEntity orderItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "stockId", source = "stockId")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "price", source = "stockId", qualifiedByName = "mapPrice")
    public abstract OrderItemEntity toEntity(final CartItemEntity cartItemEntity);

    public abstract List<OrderItemEntity> toEntityList(final List<CartItemEntity> cartItems);

    @Named("mapPrice")
    public Double mapPrice(final UUID stockId) {
        return stockServiceImpl.getStockItemById(stockId).getPrice();
    }
}
