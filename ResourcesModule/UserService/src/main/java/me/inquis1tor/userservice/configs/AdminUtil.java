package me.inquis1tor.userservice.configs;

import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminUtil {
    @Nullable
    public static UUID getAdminId() {
        System.out.println("UUID "+(UUID) RequestContextHolder.getRequestAttributes().getAttribute("ADMIN_ID", RequestAttributes.SCOPE_REQUEST));
        return (UUID) RequestContextHolder.getRequestAttributes().getAttribute("ADMIN_ID", RequestAttributes.SCOPE_REQUEST);
    }
}
