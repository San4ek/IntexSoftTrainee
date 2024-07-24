package org.example.mappers;

import org.example.dtos.CartRequest;
import org.example.dtos.CartResponse;
import org.example.entities.CartEntity;
import org.example.utils.security.LoggedAccountDetailsProvider;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class CartMapper {

    @Autowired
    private LoggedAccountDetailsProvider loggedAccountDetailsProvider;

    public abstract CartResponse toDto(final CartEntity cartEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    public abstract CartEntity toEntity(final CartRequest cartRequest);

    @AfterMapping
    public void setUserId(@MappingTarget CartEntity cartEntity) {
        cartEntity.setUserId(loggedAccountDetailsProvider.getAccountId());
    }

    public abstract void toDto(@MappingTarget CartResponse cartResponse, final CartEntity cartEntity);

    @Mapping(target = "id", ignore = true)
    public abstract void toEntity(@MappingTarget CartEntity cartEntity, final CartRequest cartRequest);
}
