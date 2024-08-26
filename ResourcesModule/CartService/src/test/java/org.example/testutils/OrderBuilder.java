package org.example.testutils;

import org.example.entities.AddressEntity;
import org.example.entities.CartEntity;
import org.example.entities.OrderEntity;

import java.util.UUID;

public class OrderBuilder {

    private UUID id = UUID.randomUUID();
    private CartEntity cart = CartBuilder.aCart().build();
    private AddressEntity address = AddressBuilder.anAddress().build();

    private OrderBuilder() {
    }

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

    public OrderEntity build() {
        return new OrderEntity(id, cart, address);
    }
}
