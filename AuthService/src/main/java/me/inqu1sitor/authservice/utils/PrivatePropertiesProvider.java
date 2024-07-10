package me.inqu1sitor.authservice.utils;

/**
 * Implementations of this interface are responsible for providing the required private properties.
 *
 * @author Alexander Sankevich
 */
public interface PrivatePropertiesProvider {

    String getAuthCode();

    String getClientSecret();

    String getClientId();

    String getRedirectUri();

    String getIssuerUri();
}
