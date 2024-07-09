package me.inqu1sitor.authservice.interceptors;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import me.inqu1sitor.authservice.utils.PrivatePropertiesProvider;
import org.springframework.stereotype.Component;

/**
 * An implementation of {@link RequestInterceptor} for pasting
 * required secret code in HTTP request header during auth and
 * user services conversation.
 *
 * @author Alexander Sankevich
 */
@Component
@RequiredArgsConstructor
public class ClientInterceptor implements RequestInterceptor {

    private final PrivatePropertiesProvider privatePropertiesProvider;

    @Override
    public void apply(final RequestTemplate requestTemplate) {
        requestTemplate.header("Auth-Code", privatePropertiesProvider.getAuthCode());
    }
}
