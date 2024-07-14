package me.inquis1tor.userservice.services;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.inquis1tor.userservice.utils.PrivatePropertiesProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

/**
 * Validates every request to annotated endpoint
 * for having required auth code in their headers.
 *
 * @author Alexander Sankevich
 */
@Slf4j
@Service("securityService")
@RequiredArgsConstructor
@RequestScope
public class SecurityService {

    private final HttpServletRequest request;
    private final PrivatePropertiesProvider privatePropertiesProvider;

    /**
     * Implements validation logic.
     *
     * @return {@code false} if auth code not found or invalid
     */
    public boolean hasAuthCode() {
        String code = request.getHeader("Auth-Code");
        return code != null && code.equals(privatePropertiesProvider.getAuthCode());
    }
}
