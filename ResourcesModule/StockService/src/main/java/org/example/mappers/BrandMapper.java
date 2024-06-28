package org.example.mappers;

import org.example.dtos.BrandRequest;
import org.example.dtos.BrandResponse;
import org.example.entities.BrandEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    @Mapping(source = "name", target = "name")
    BrandResponse toDto(BrandEntity brand);

    @Mapping(source = "name", target = "name")
    BrandEntity toEntity(BrandRequest brandRequest);
}
