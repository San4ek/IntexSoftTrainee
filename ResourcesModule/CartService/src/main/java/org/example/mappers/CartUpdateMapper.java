package org.example.mappers;

import org.example.dtos.CartUpdateRequest;
import org.example.dtos.CartUpdateResponse;
import org.example.entities.CartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class CartUpdateMapper {

    public abstract CartUpdateResponse toDto(CartEntity cartEntity);

    @Mapping(target = "id", ignore = true)
    public abstract CartEntity toEntity(CartUpdateRequest cartUpdateRequest);

    public abstract void toDto(@MappingTarget CartUpdateResponse cartUpdateResponse, CartEntity cartEntity);

    @Mapping(target = "id", ignore = true)
    public abstract void toEntity(@MappingTarget CartEntity cartEntity, CartUpdateRequest cartUpdateRequest);
}