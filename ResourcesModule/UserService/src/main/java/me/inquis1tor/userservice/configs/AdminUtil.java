package me.inquis1tor.userservice.configs;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.UUID;

@Component
@RequestScope
@RequiredArgsConstructor
public class AdminUtil {

    private final HttpServletRequest httpServletRequest;

    @Nullable
    public UUID getAdminId() {
        return UUID.fromString(httpServletRequest.getHeader("Administrator"));
    }
}
