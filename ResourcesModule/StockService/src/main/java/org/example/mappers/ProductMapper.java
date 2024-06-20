package org.example.mappers;

import org.example.dtos.ProductRequest;
import org.example.dtos.ProductResponse;
import org.example.entities.ProductEntity;
import org.example.enums.CurrencyEnum;
import org.example.enums.TypeEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "brand.id", target = "brandId")
    @Mapping(source = "currency", target = "currency")
    @Mapping(source = "price", target = "price")
    ProductResponse toDto(ProductEntity productEntity);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "brandId", target = "brand.id")
    @Mapping(source = "currency", target = "currency")
    @Mapping(source = "price", target = "price")
    ProductEntity toEntity(ProductRequest productRequest);

    default TypeEnum stringToTypeEnum(String type) {
        return TypeEnum.valueOf(type.toUpperCase());
    }

    default String typeEnumToString(TypeEnum typeEnum) {
        return typeEnum.name();
    }

    default CurrencyEnum stringToCurrencyEnum(String currency) {
        return CurrencyEnum.valueOf(currency.toUpperCase());
    }

    default String currencyEnumToString(CurrencyEnum currencyEnum) {
        return currencyEnum.name();
    }

}
