package org.example.mappers;

import org.example.dtos.ProductRequest;
import org.example.dtos.ProductResponse;
import org.example.entities.BrandEntity;
import org.example.entities.ProductEntity;
import org.example.repositories.BrandRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {BrandMapper.class})
public abstract class ProductMapper {

    @Autowired
    private BrandRepository brandRepository;

    @Mapping(target = "brand", source = "brand")
    public abstract ProductResponse toDto(ProductEntity productEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "brand", source = "brandId", qualifiedByName = "mapBrand")
    public abstract ProductEntity toEntity(ProductRequest productRequest);

    @Mapping(target = "brand", source = "brand")
    public abstract void toDto(@MappingTarget ProductResponse productResponse, ProductEntity productEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "brand", source = "brandId", qualifiedByName = "mapBrand")
    public abstract void toEntity(@MappingTarget ProductEntity productEntity, ProductRequest productRequest);

    @Named("mapBrand")
    public BrandEntity mapBrand(UUID brandId) {
        return brandRepository.getById(brandId);
    }

}
