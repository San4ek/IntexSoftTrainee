package me.inqu1sitor.authservice.services.impl;

import lombok.RequiredArgsConstructor;
import me.inqu1sitor.authservice.entities.AccountEntity;
import me.inqu1sitor.authservice.entities.AccountRole;
import me.inqu1sitor.authservice.entities.AccountStatus;
import me.inqu1sitor.authservice.exceptions.AccountNotFoundException;
import me.inqu1sitor.authservice.repositories.AccountRepository;
import me.inqu1sitor.authservice.services.AccountFinderService;
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
     * Returns the {@link AccountEntity} with {@code ACTIVE} {@link AccountStatus status}
     * and any not {@code ADMIN} {@link AccountRole role} identified by the provided parameter
     * {@code accountId} or throws {@link AccountNotFoundException exception} if not found.
     *
     * @param accountId the {@link AccountEntity} id
     * @return the {@link AccountEntity} if found, otherwise throws {@link AccountNotFoundException}
     * @throws AccountNotFoundException if {@link AccountEntity} not found
     */
    @Transactional
    public AccountEntity findActiveNotAdmin(final UUID accountId) {
        return accountRepository.
                findByIdAndRoleNotAndStatus(accountId, AccountRole.ADMIN, AccountStatus.ACTIVE).
                orElseThrow(() -> new AccountNotFoundException(accountId));
    }

    /**
     * Returns the {@link AccountEntity} with {@code BLOCKED} {@link AccountStatus status}
     * and any not {@code ADMIN} {@link AccountRole role} identified by the provided parameter
     * {@code accountId} or throws {@link AccountNotFoundException exception} if not found.
     *
     * @param accountId the {@link AccountEntity} id
     * @return the {@link AccountEntity} if found, otherwise throws {@link AccountNotFoundException}
     * @throws AccountNotFoundException if {@link AccountEntity} not found
     */
    @Transactional
    public AccountEntity findBlockedNotAdmin(final UUID accountId) {
        return accountRepository.
                findByIdAndRoleNotAndStatus(accountId, AccountRole.ADMIN, AccountStatus.BLOCKED).
                orElseThrow(() -> new AccountNotFoundException(accountId));
    }

    /**
     * Returns the {@link AccountEntity} with {@code ACTIVE} {@link AccountStatus status}
     * and any {@link AccountRole role} identified by the provided parameter {@code accountId}
     * or throws {@link AccountNotFoundException exception} if not found.
     *
     * @param accountId the {@link AccountEntity} id
     * @return the {@link AccountEntity} if found, otherwise throws {@link AccountNotFoundException}
     * @throws AccountNotFoundException if {@link AccountEntity} not found
     */
    @Transactional
    public AccountEntity findActiveAny(final UUID accountId) {
        return accountRepository.
                findByIdAndStatus(accountId, AccountStatus.ACTIVE).
                orElseThrow(() -> new AccountNotFoundException(accountId));
    }
}
