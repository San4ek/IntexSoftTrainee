package me.inquis1tor.userservice.rabbit;

import me.inquis1tor.userservice.entities.AccountEntity;

import java.util.UUID;

/**
 * Implementations of this interface are responsible for receiving
 * notification about {@link AccountEntity} deleting.
 *
 * @author Alexander Sankevich
 */
public interface AccountDeletedReceiver {

    void receive(UUID accountId);
}
