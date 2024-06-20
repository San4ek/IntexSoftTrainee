package org.example.mappers;

import org.example.dtos.StockItemRequest;
import org.example.dtos.StockItemResponse;
import org.example.entities.StockEntity;
import org.example.enums.ColorEnum;
import org.example.enums.SizeEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
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

    default ColorEnum stringToColorEnum(String color) {
        return ColorEnum.valueOf(color.toUpperCase());
    }

    default String colorEnumToString(ColorEnum colorEnum) {
        return colorEnum.name();
    }

    default SizeEnum stringToSizeEnum(String size) {
        return SizeEnum.valueOf(size.toUpperCase());
    }

    default String sizeEnumToString(SizeEnum sizeEnum) {
        return sizeEnum.name();
    }
}
