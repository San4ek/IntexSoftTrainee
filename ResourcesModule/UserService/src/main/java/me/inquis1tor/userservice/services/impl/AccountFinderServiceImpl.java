package me.inquis1tor.userservice.services.impl;

import lombok.RequiredArgsConstructor;
import me.inquis1tor.userservice.entities.AccountEntity;
import me.inquis1tor.userservice.exceptions.AccountNotFoundException;
import me.inquis1tor.userservice.repositories.AccountRepository;
import me.inquis1tor.userservice.services.AccountFinderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * An implementation of an {@link AccountFinderService} that uses a
 * {@link AccountRepository} for {@link AccountEntity} searching.
 *
 * @author Alexander Sankevich
 */
@Service
@RequiredArgsConstructor
public class AccountFinderServiceImpl implements AccountFinderService {

    private final AccountRepository accountRepository;

    /**
     * Returns the {@link AccountEntity} with {@code ACTIVE} {@link AccountEntity.Status status}
     * and any not {@code ADMIN} {@link AccountEntity.Role role} identified by the provided parameter
     * {@code accountId} or throws {@link AccountNotFoundException exception} if not found.
     *
     * @param accountId the {@link AccountEntity} id
     * @return the {@link AccountEntity} if found, otherwise throws {@link AccountNotFoundException}
     * @throws AccountNotFoundException if {@link AccountEntity} not found
     */
    @Transactional
    public AccountEntity findActiveNotAdmin(final UUID accountId) {
        return accountRepository.
                findByIdAndRoleNotAndStatus(accountId, AccountEntity.Role.ADMIN, AccountEntity.Status.ACTIVE).
                orElseThrow(() -> new AccountNotFoundException(accountId));
    }

    /**
     * Returns the {@link AccountEntity} with {@code BLOCKED} {@link AccountEntity.Status status}
     * and any not {@code ADMIN} {@link AccountEntity.Role role} identified by the provided parameter
     * {@code accountId} or throws {@link AccountNotFoundException exception} if not found.
     *
     * @param accountId the {@link AccountEntity} id
     * @return the {@link AccountEntity} if found, otherwise throws {@link AccountNotFoundException}
     * @throws AccountNotFoundException if {@link AccountEntity} not found
     */
    @Transactional
    public AccountEntity findBlockedNotAdmin(final UUID accountId) {
        return accountRepository.
                findByIdAndRoleNotAndStatus(accountId, AccountEntity.Role.ADMIN, AccountEntity.Status.BLOCKED).
                orElseThrow(() -> new AccountNotFoundException(accountId));
    }

    /**
     * Returns the {@link AccountEntity} with {@code ACTIVE} {@link AccountEntity.Status status}
     * and any {@link AccountEntity.Role role} identified by the provided parameter {@code accountId}
     * or throws {@link AccountNotFoundException exception} if not found.
     *
     * @param accountId the {@link AccountEntity} id
     * @return the {@link AccountEntity} if found, otherwise throws {@link AccountNotFoundException}
     * @throws AccountNotFoundException if {@link AccountEntity} not found
     */
    @Transactional
    public AccountEntity findActiveAny(final UUID accountId) {
        return accountRepository.
                findById(accountId).
                orElseThrow(() -> new AccountNotFoundException(accountId));
    }
}
