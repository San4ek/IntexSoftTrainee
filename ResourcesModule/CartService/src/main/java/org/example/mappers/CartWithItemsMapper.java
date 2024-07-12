package org.example.mappers;

import org.example.dtos.CartWithItemsResponse;
import org.example.entities.CartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CartItemInCartMapper.class})
public abstract class CartWithItemsMapper {

    @Mapping(target = "cartItems", source = "cartItems")
    public abstract CartWithItemsResponse toDto(CartEntity cartEntity);
}
