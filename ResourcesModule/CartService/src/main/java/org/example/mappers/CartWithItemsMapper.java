package org.example.mappers;

import org.example.dtos.CartWithItemsResponse;
import org.example.entities.CartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {StockItemMapper.class})
public abstract class CartWithItemsMapper {

    @Mapping(target = "cartItems", source = "cartItems")
    public abstract CartWithItemsResponse toDto(final CartEntity cartEntity);
}
