package org.example.mappers;

import org.example.dtos.CartRequest;
import org.example.dtos.CartResponse;
import org.example.entities.CartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class CartMapper {

    public abstract CartResponse toDto(CartEntity cartEntity);

    @Mapping(target = "id", ignore = true)
    public abstract CartEntity toEntity(CartRequest cartRequest);

    public abstract void toDto(@MappingTarget CartResponse cartResponse, CartEntity cartEntity);

    @Mapping(target = "id", ignore = true)
    public abstract void toEntity(@MappingTarget CartEntity cartEntity, CartRequest cartRequest);

}
