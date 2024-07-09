package me.inqu1sitor.authservice.utils.impl;

import lombok.RequiredArgsConstructor;
import me.inqu1sitor.authservice.utils.PrivatePropertiesProvider;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * A {@link PropertySource} implementation of the {@link PrivatePropertiesProvider}.
 *
 * @author Alexander Sankevich
 */
@Component
@RequiredArgsConstructor
@PropertySource("classpath:gitignore/hidden.properties")
public class PrivatePropertiesProviderImpl implements PrivatePropertiesProvider {

    private final Environment environment;

    /**
     * Returns the {@code auth.code} property as string, or throws {@link IllegalArgumentException} if not found.
     *
     * @return the property as string if found, otherwise throws {@link IllegalArgumentException}
     * @throws IllegalArgumentException if property not found or {@code null}
     */
    public String getAuthCode() {
        String property = getProperty("auth.code");
        assertPropertyNotNull(property);
        return property;
    }

    /**
     * Returns the {@code client.secret} property as string, or throws {@link IllegalArgumentException} if not found.
     *
     * @return the property as string if found, otherwise throws {@link IllegalArgumentException}
     * @throws IllegalArgumentException if property not found or {@code null}
     */
    public String getClientSecret() {
        String property = getProperty("client.secret");
        assertPropertyNotNull(property);
        return property;
    }

    /**
     * Returns the {@code client.id} property as string, or throws {@link IllegalArgumentException} if not found.
     *
     * @return the property as string if found, otherwise throws {@link IllegalArgumentException}
     * @throws IllegalArgumentException if property not found or {@code null}
     */
    public String getClientId() {
        String property = getProperty("client.id");
        assertPropertyNotNull(property);
        return property;
    }

    /**
     * Returns the {@code client.redirect.uri} property as string, or throws {@link IllegalArgumentException} if not found.
     *
     * @return the property as string if found, otherwise throws {@link IllegalArgumentException}
     * @throws IllegalArgumentException if property not found or {@code null}
     */
    public String getRedirectUri() {
        String property = getProperty("client.redirect.uri");
        assertPropertyNotNull(property);
        return property;
    }

    /**
     * Returns the {@code auth.issuer.uri} property as string, or throws {@link IllegalArgumentException} if not found.
     *
     * @return the property as string if found, otherwise throws {@link IllegalArgumentException}
     * @throws IllegalArgumentException if property not found or {@code null}
     */
    public String getIssuerUri() {
        String property = getProperty("auth.issuer.uri");
        assertPropertyNotNull(property);
        return property;
    }

    @Nullable
    private String getProperty(final String property) {
        return environment.getProperty(property);
    }

    private void assertPropertyNotNull(final Object object) {
        Assert.notNull(object, "property " + object + " required");
    }
}
