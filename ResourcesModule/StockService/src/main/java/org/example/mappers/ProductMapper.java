package org.example.mappers;

import org.example.dtos.ProductRequest;
import org.example.dtos.ProductResponse;
import org.example.entities.ProductEntity;
import org.example.repositories.BrandRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BrandMapper.class})
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
}
