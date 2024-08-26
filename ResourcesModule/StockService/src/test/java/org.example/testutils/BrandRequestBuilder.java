package org.example.testutils;

import org.example.dtos.BrandRequest;

public class BrandRequestBuilder {

    private String name = "Marko";

    private BrandRequestBuilder() {}

    public static BrandRequestBuilder aBrandRequest() {
        return new BrandRequestBuilder();
    }

    public BrandRequestBuilder withName(final String name) {
        this.name = name;
        return this;
    }

    public BrandRequest build() {
        return new BrandRequest(name);
    }
}
