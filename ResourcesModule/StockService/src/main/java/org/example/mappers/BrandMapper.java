package org.example.mappers;

import org.example.dtos.BrandRequest;
import org.example.dtos.BrandResponse;
import org.example.entities.BrandEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class BrandMapper {

    public abstract BrandResponse toDto(BrandEntity brandEntity);

    @Mapping(target = "id", ignore = true)
    public abstract BrandEntity toEntity(BrandRequest brandRequest);

    public abstract void toDto(@MappingTarget BrandResponse brandResponse, BrandEntity brand);

    @Mapping(target = "id", ignore = true)
    public abstract void toEntity(@MappingTarget BrandEntity brandEntity, BrandRequest brandRequest);

}
