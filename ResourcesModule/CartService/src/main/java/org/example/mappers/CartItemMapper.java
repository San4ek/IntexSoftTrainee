package org.example.mappers;

import org.example.dtos.CartItemRequest;
import org.example.dtos.CartItemResponse;
import org.example.dtos.CartRequest;
import org.example.dtos.StockItemAmount;
import org.example.entities.CartEntity;
import org.example.entities.CartItemEntity;
import org.example.repositories.CartRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = {CartMapper.class})
public abstract class CartItemMapper {

    @Autowired
    private CartRepository cartRepository;

    @Mapping(target = "cart", source = "cart")
    public abstract CartItemResponse toDto(CartItemEntity cartItemEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cart", source = "cartId", qualifiedByName = "mapCart")
    public abstract CartItemEntity toEntity(CartItemRequest cartItemRequest);

    @Mapping(target = "cart", source = "cart")
    public abstract void toDto(@MappingTarget CartItemResponse cartItemResponse, CartItemEntity cartItemEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cart", source = "cartId", qualifiedByName = "mapCart")
    public abstract void toEntity(@MappingTarget CartItemEntity cartItemEntity, CartItemRequest cartItemRequest);

    public abstract List<CartItemResponse> toListDto(List<CartItemEntity> cartItemEntities);

    @Named("mapCart")
    public CartEntity mapCart(UUID cartId) {
        return cartRepository.getById(cartId);
    }
}
