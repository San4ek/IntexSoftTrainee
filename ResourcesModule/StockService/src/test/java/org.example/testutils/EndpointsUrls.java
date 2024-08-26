package org.example.testutils;

import lombok.Getter;

@Getter
public enum EndpointsUrls {
    BRANDS_GET_PUT_DELETE("/api/brands/"),
    BRANDS_POST("/api/brands"),
    PRODUCTS_GET_PUT_DELETE("/api/products/"),
    PRODUCTS_POST("/api/products"),
    STOCK_MODER_GET_PUT_DELETE("/api/stock/"),
    STOCK_MODER_POST("/api/stock"),
    STOCK_MODER_UTILIZATION("/utilization"),
    STOCK_USER_NAME("/api/user-stock/name/"),
    STOCK_USER_ATTRIBUTES("/api/user-stock/attributes"),
    STOCK_USER_GET_PUT("/api/user-stock/");

    private final String path;

    EndpointsUrls(String path) {
        this.path = path;
    }
}
