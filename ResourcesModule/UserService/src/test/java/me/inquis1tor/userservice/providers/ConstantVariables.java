package me.inquis1tor.userservice.providers;

import lombok.Getter;

@Getter
public enum ConstantVariables {
    UUID("c0a80065-90a2-1cb0-8190-a20de91f0000"),
    AUTH_CODE("Auth_Code"),
    ACCOUNT_ID("accountId"),
    ADMIN_ID("adminId");

    private final String val;

    ConstantVariables(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return val;
    }
}
