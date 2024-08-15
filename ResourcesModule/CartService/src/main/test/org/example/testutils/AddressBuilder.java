package org.example.testutils;

import org.example.entities.AddressEntity;

import java.util.UUID;

public class AddressBuilder {

    private UUID id = UUID.randomUUID();
    private String address = "Mihalova 52";

    private AddressBuilder() {}

    public static AddressBuilder anAddress() {
        return new AddressBuilder();
    }

    public AddressBuilder withId(final UUID id) {
        this.id = id;
        return this;
    }

    public AddressBuilder withAddress(final String address) {
        this.address = address;
        return this;
    }

    public AddressEntity build() {
        return new AddressEntity(id, address);
    }
}
