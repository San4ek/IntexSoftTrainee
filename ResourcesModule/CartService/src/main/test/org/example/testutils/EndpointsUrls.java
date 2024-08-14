package org.example.testutils;

import lombok.Getter;

@Getter
public enum EndpointsUrls {
    ADDRESSES_PUT_DELETE("/api/addresses/"),
    ADDRESSES_GET_POST("/api/addresses"),
    CART_GET_PUT_DELETE("/api/carts/"),
    CART_POST("/api/carts"),
    CART_ADD_REMOVE_ITEM("/api/carts/item"),
    CART_DELETE_BY_STOCK_ID("/api/carts/items/"),
    ORDERS_GET_DELETE("/api/orders/"),
    ORDERS_POST("/api/orders"),
    ORDERS_DELETE_BY_ADDRESS("/api/orders/address");

    private final String path;

    EndpointsUrls(String path) {
        this.path = path;
    }
}
