package me.inqu1sitor.authservice.interceptors;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConfigurationProperties
@PropertySource("classpath:gitignore/auth.yml")
public class ClientInterceptor implements RequestInterceptor {

    private final Environment environment;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Auth-Code", environment.getProperty("auth.code"));
    }
}
