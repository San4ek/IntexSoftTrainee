package me.inquis1tor.userservice.services;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

@Slf4j
@Service("securityService")
@RequiredArgsConstructor
@RequestScope
public class SecurityService {

    private final HttpServletRequest request;

    public boolean hasCode(String requiredCode) {
        String code=request.getHeader("Auth-code");

        return code!=null && code.equals(requiredCode);
    }
}
