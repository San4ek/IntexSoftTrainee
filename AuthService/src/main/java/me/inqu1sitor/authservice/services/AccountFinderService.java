package me.inqu1sitor.authservice.services;

import me.inqu1sitor.authservice.entities.AccountEntity;

import java.util.UUID;

/**
 * Implementations of this interface are responsible for {@link AccountEntity} searching.
 *
 * @author Alexander Sankevich
 */
public interface AccountFinderService {

    AccountEntity findActiveNotAdmin(UUID accountId);

    AccountEntity findBlockedNotAdmin(UUID accountId);

    AccountEntity findActiveAny(UUID accountId);
}
