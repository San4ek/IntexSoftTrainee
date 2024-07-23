package org.example.mappers;

import org.example.dtos.OrderRequest;
import org.example.dtos.OrderResponse;
import org.example.entities.AddressEntity;
import org.example.entities.CartEntity;
import org.example.entities.CartItemEntity;
import org.example.entities.OrderEntity;
import org.example.repositories.AddressRepository;
import org.example.repositories.CartRepository;
import org.example.services.impl.CurrencyConversionServiceImpl;
import org.example.services.impl.StockServiceImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

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
    private CurrencyConversionServiceImpl currencyConversionService;

    @Mapping(target = "id", source = "id")
    @Mapping(target = "cart", source = "cart")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "convertedCost", source = "cart", qualifiedByName = "mapConvertedCost")
    @Mapping(target = "cost", source = "cart", qualifiedByName = "mapCost")
    public abstract OrderResponse toDto(final OrderEntity orderEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cart", source = "cartId", qualifiedByName = "mapCart")
    @Mapping(target = "address", source = "addressId", qualifiedByName = "mapAddress")
    public abstract OrderEntity toEntity(final OrderRequest orderRequest);

    @Named("mapCart")
    public CartEntity mapCart(final UUID cartId) {
        return cartRepository.getById(cartId);
    }

    @Named("mapAddress")
    public AddressEntity mapAddress(final UUID addressId) {
        return addressRepository.getById(addressId);
    }

    @Named("mapCost")
    public Double mapCost(final CartEntity cart) {
        Double cost = 0d;
        for (CartItemEntity cartItem : cart.getCartItems()) {
            Double price = stockService.getStockItemById(cartItem.getStockId()).getPrice();
            cost += cartItem.getAmount() * price;
        }
        return cost;
    }

    @Named("mapConvertedCost")
    public Double mapConvertedCost(final CartEntity cart) {
        Double convertedCost = 0d;
        for (CartItemEntity cartItem : cart.getCartItems()) {
            Double price = stockService.getStockItemById(cartItem.getStockId()).getPrice();
            convertedCost += cartItem.getAmount() * price;
        }
        convertedCost = currencyConversionService.convertCurrency("BYN", cart.getCurrency().name(), convertedCost);
        return convertedCost;
    }
}
