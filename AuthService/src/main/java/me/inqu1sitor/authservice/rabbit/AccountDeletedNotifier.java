package me.inqu1sitor.authservice.rabbit;

import me.inqu1sitor.authservice.entities.AccountEntity;

import java.util.UUID;

/**
 * Implementations of this interface are responsible for notifying
 * other services about {@link AccountEntity} deleting.
 *
 * @author Alexander Sankevich
 */
@FunctionalInterface
public interface AccountDeletedNotifier {

    void notifyAbout(UUID accountId);
}
