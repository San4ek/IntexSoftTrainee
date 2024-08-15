package org.example.mappers;

import org.example.dtos.PagedStockItemsResponse;
import org.example.dtos.StockItemRequest;
import org.example.dtos.StockItemResponse;
import org.example.entities.ProductEntity;
import org.example.entities.StockEntity;
import org.example.repositories.ProductRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public abstract class StockItemMapper {

    @Autowired
    private ProductRepository productRepository;

    @Mapping(target = "product", source = "product")
    public abstract StockItemResponse toDto(final StockEntity stockEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", source = "productId", qualifiedByName = "mapProduct")
    public abstract StockEntity toEntity(final StockItemRequest stockItemRequest);

    @Mapping(target = "product", source = "product")
    public abstract void toDto(@MappingTarget StockItemResponse stockItemResponse, final StockEntity stockEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", source = "productId", qualifiedByName = "mapProduct")
    public abstract void toEntity(@MappingTarget StockEntity stockEntity, final StockItemRequest stockItemCreateRequest);

    public abstract List<StockItemResponse> toDto(final List<StockEntity> stockEntityList);

    @Named("mapProduct")
    public ProductEntity mapProduct(final UUID productId) {
        return productRepository.getById(productId);
    }

    public PagedStockItemsResponse toDto(final Page<StockEntity> stockItems) {
        Page<StockItemResponse> stockItemResponses = stockItems.map(this::toDto);
        return new PagedStockItemsResponse(stockItemResponses);
    }
}
