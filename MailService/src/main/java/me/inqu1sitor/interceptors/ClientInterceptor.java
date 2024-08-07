package me.inqu1sitor.interceptors;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * An implementation of {@link RequestInterceptor} for pasting
 * required secret code in HTTP request header during mail and
 * user services conversation.
 *
 * @author Alexander Sankevich
 */
@Component
public class ClientInterceptor implements RequestInterceptor {

    @Value("${auth.code}")
    private String authCode;

    @Override
    public void apply(final RequestTemplate requestTemplate) {
        requestTemplate.header("Auth-Code", authCode);
    }
}
