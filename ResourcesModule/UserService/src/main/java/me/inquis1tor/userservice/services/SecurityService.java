package me.inquis1tor.userservice.services;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("securityService")
@RequiredArgsConstructor
public class SecurityService {

    private final HttpServletRequest httpServletRequest;

    public boolean hasCode(String code) {
        return httpServletRequest.getAttribute("Auth code").equals(code);
    }
}
