package org.example.testutils;

import org.example.entities.BrandEntity;

import java.util.UUID;

public class BrandBuilder {

    private UUID id = UUID.randomUUID();
    private String name = "Brand Name";

    private BrandBuilder() {}

    public static BrandBuilder aBrand() {
        return new BrandBuilder();
    }

    public BrandBuilder withId(final UUID id) {
        this.id = id;
        return this;
    }

    public BrandBuilder withName(final String name) {
        this.name = name;
        return this;
    }

    public BrandEntity build() {
        return new BrandEntity(id, name);
    }
}
