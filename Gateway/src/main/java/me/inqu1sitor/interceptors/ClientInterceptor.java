package me.inqu1sitor.interceptors;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import me.inqu1sitor.utils.LoggedAccountDetailsHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientInterceptor implements RequestInterceptor {

    private final LoggedAccountDetailsHolder holder;

    @Override
    public void apply(final RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", "Bearer " + holder.getTokenValue());
    }
}
