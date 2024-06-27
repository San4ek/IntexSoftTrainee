package org.example.mappers;

import org.example.dtos.StockItemRequest;
import org.example.dtos.StockItemResponse;
import org.example.entities.StockEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface StockItemMapper {

    @Mapping(source = "size", target = "size")
    @Mapping(source = "color", target = "color")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "amount", target = "amount")
    StockItemResponse toDto(StockEntity stockEntity);

    @Mapping(source = "size", target = "size")
    @Mapping(source = "color", target = "color")
    @Mapping(source = "productId", target = "product.id")
    @Mapping(source = "amount", target = "amount")
    StockEntity toEntity(StockItemRequest stockItemCreateRequest);

    List<StockItemResponse> toDto(List<StockEntity> stockEntityList);

    List<StockEntity> toEntity(List<StockItemRequest> stockItemRequestList);

}
