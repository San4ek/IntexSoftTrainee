package me.inquis1tor.userservice.providers;

import lombok.Getter;

@Getter
public enum EndpointsUrls {
    ACCOUNTS("/api/accounts"),
    ACCOUNTS_ALL("/api/accounts/all"),
    ACCOUNTS_BLOCK("/api/accounts/block"),
    ACCOUNTS_UNBLOCK("/api/accounts/unblock"),
    ACCOUNTS_CREDENTIALS("/api/accounts/credentials"),
    PERSONAL_INFOS("/api/personal-infos"),;

    private final String path;

    EndpointsUrls(String path) {
        this.path = path;
    }
}
