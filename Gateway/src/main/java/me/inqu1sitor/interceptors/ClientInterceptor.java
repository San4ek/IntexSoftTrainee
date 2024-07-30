package me.inqu1sitor.interceptors;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import me.inqu1sitor.utils.LoggedAccountDetailsProvider;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * Intercepts {@link FeignClient} requests and add to them JWT,
 * obtained from received HTTP request.
 *
 * @author Alexander Sankevich
 */
@Component
@RequiredArgsConstructor
public class ClientInterceptor implements RequestInterceptor {

    private final LoggedAccountDetailsProvider holder;

    @Override
    public void apply(final RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", "Bearer " + holder.getTokenValue());
    }
}
