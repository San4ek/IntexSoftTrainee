package org.example.mappers;

import org.example.dtos.OrderRequest;
import org.example.dtos.OrderResponse;
import org.example.entities.CartEntity;
import org.example.entities.CartItemEntity;
import org.example.entities.OrderEntity;
import org.example.repositories.CartRepository;
import org.example.services.impl.StockServiceImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {CartWithItemsMapper.class})
public abstract class OrderMapper {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private StockServiceImpl stockService;

    @Mapping(target = "id", source = "id")
    @Mapping(target = "cart", source = "cart")
    @Mapping(target = "cost", source = "cart", qualifiedByName = "mapCost")
    public abstract OrderResponse toDto(OrderEntity orderEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cart", source = "cartId", qualifiedByName = "mapCart")
    public abstract OrderEntity toEntity(OrderRequest orderRequest);

    @Named("mapCart")
    public CartEntity mapCart(UUID cartId) {
        return cartRepository.getById(cartId);
    }

    @Named("mapCost")
    public Double mapCost(CartEntity cart) {
        Double cost = 0d;
        for (CartItemEntity cartItem : cart.getCartItems()) {
            Double price = stockService.getStockItemById(cartItem.getStockId()).getPrice();
            cost += cartItem.getAmount() * price;
        }
        return cost;
    }
}
