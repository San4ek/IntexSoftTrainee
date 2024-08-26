package org.example.testutils;

import org.example.dtos.AddressRequest;

public class AddressRequestBuilder {

    private String address = "Mihalova 50";

    private AddressRequestBuilder() {
    }

    public static AddressRequestBuilder anAddressRequest() {
        return new AddressRequestBuilder();
    }

    public AddressRequestBuilder withAddress(final String address) {
        this.address = address;
        return this;
    }

    public AddressRequest build() {
        return new AddressRequest(address);
    }
}
