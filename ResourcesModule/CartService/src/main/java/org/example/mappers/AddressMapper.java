package org.example.mappers;

import org.example.dtos.AddressRequest;
import org.example.dtos.AddressResponse;
import org.example.entities.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class AddressMapper {

    public abstract AddressResponse toDto(final AddressEntity addressEntity);

    @Mapping(target = "id", ignore = true)
    public abstract AddressEntity toEntity(final AddressRequest addressRequest);

    public abstract void toDto(@MappingTarget AddressResponse addressResponse, final AddressEntity addressEntity);

    @Mapping(target = "id", ignore = true)
    public abstract void toEntity(@MappingTarget AddressEntity addressEntity, final AddressRequest addressRequest);

    public abstract List<AddressResponse> toDto(final List<AddressEntity> addressEntities);

    public abstract List<AddressEntity> toEntity(final List<AddressRequest> addressEntities);
}
