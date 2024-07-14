package me.inquis1tor.userservice.utils.impl;

import lombok.RequiredArgsConstructor;
import me.inquis1tor.userservice.utils.PrivatePropertiesProvider;
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

    @Nullable
    private String getProperty(final String property) {
        return environment.getProperty(property);
    }

    private void assertPropertyNotNull(final Object object) {
        Assert.notNull(object, "property " + object + " required");
    }
}
