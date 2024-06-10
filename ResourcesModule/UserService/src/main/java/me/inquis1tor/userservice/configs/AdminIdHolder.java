package me.inquis1tor.userservice.configs;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminIdHolder {
    private static UUID adminId;

    public static void setAdminId(UUID adminId) {
        AdminIdHolder.adminId=adminId;
    }

    public static void clear() {
        AdminIdHolder.adminId=null;
    }

    public static UUID getAdminId() {
        return AdminIdHolder.adminId;
    }
}
