package org.example.testutils;

import org.example.entities.AddressEntity;
import org.example.entities.CartEntity;
import org.example.entities.OrderEntity;
import org.example.entities.OrderItemEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderBuilder {

    private UUID id = UUID.randomUUID();
    private CartEntity cart = CartBuilder.aCart().build();
    private AddressEntity address = AddressBuilder.anAddress().build();
    private List<OrderItemEntity> orderItems = new ArrayList<>();
    private Double totalCost = 0.0D;

    private OrderBuilder() {}

    public static OrderBuilder anOrder() {
        return new OrderBuilder();
    }

    public OrderBuilder withId(final UUID id) {
        this.id = id;
        return this;
    }

    public OrderBuilder withCart(final CartEntity cart) {
        this.cart = cart;
        return this;
    }

    public OrderBuilder withAddress(final AddressEntity address) {
        this.address = address;
        return this;
    }

    public OrderBuilder withOrderItems(final List<OrderItemEntity> orderItems) {
        this.orderItems = orderItems;
        return this;
    }

    public OrderBuilder withTotalCost(final Double totalCost) {
        this.totalCost = totalCost;
        return this;
    }

    public OrderEntity build() {
        return new OrderEntity(id, cart, address, orderItems, totalCost);
    }
}
