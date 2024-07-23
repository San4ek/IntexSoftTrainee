package me.inquis1tor.userservice.providers;

import lombok.Getter;

@Getter
public enum StandartVariables {
    UUID("c0a80065-90a2-1cb0-8190-a20de91f0000");

    private final String val;

    StandartVariables(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return val;
    }
}
