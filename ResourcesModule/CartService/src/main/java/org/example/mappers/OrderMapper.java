package org.example.mappers;

import org.example.dtos.OrderRequest;
import org.example.dtos.OrderResponse;
import org.example.entities.*;
import org.example.repositories.AddressRepository;
import org.example.repositories.CartRepository;
import org.example.services.impl.CurrencyConversionServiceImpl;
import org.example.services.impl.StockServiceImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = {CartWithItemsMapper.class, AddressMapper.class})
public abstract class OrderMapper {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private StockServiceImpl stockService;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private CurrencyConversionServiceImpl currencyConversionService;

    @Mapping(target = "id", source = "id")
    @Mapping(target = "cart", source = "cart")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "totalCost", source = "totalCost")
    @Mapping(target = "orderItems", source = "orderItems")
    public abstract OrderResponse toDto(final OrderEntity orderEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cart", source = "cartId", qualifiedByName = "mapCart")
    @Mapping(target = "address", source = "addressId", qualifiedByName = "mapAddress")
    @Mapping(target = "totalCost", source = "cartId", qualifiedByName = "mapCost")
    public abstract OrderEntity toEntity(final OrderRequest orderRequest);

    @Named("mapCart")
    public CartEntity mapCart(final UUID cartId) {
        return cartRepository.getById(cartId);
    }

    @Named("mapAddress")
    public AddressEntity mapAddress(final UUID addressId) {
        return addressRepository.getById(addressId);
    }

    public void mapOrderItems(@MappingTarget OrderEntity orderEntity, final UUID cartId) {
        List<OrderItemEntity> orderItems = new ArrayList<>();
        CartEntity cart = cartRepository.getById(cartId);
        for (CartItemEntity cartItem : cart.getCartItems()) {
            OrderItemEntity orderItem = orderItemMapper.toEntity(cartItem);
            orderItem.setOrder(orderEntity);
            orderItems.add(orderItem);
        }
        orderEntity.setOrderItems(orderItems);
    }

    @Named("mapCost")
    public Double mapCost(final UUID cartId) {
        CartEntity cart = cartRepository.getById(cartId);
        List<OrderItemEntity> orderItems = orderItemMapper.toEntityList(cart.getCartItems());
        return orderItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getAmount())
                .sum();
    }
}
