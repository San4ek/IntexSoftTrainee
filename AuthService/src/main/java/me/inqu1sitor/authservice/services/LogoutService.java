package me.inqu1sitor.authservice.services;

import java.util.UUID;

/**
 * Implementations of this interface are responsible for logout from account.
 *
 * @author Alexander Sankevich
 */
public interface LogoutService {

    void logout(String tokenValue);

    void logout();

    void logoutAll(UUID accountId);

    void logoutAll();
}
