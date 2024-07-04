package me.inqu1sitor.authservice.services;

import lombok.RequiredArgsConstructor;
import me.inqu1sitor.authservice.entities.AccountEntity;
import me.inqu1sitor.authservice.repositories.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountFinderService {

    private final AccountRepository accountRepository;

    @Transactional
    public AccountEntity findActiveNotAdmin(final UUID accountId) {
        return accountRepository.findByIdAndRoleNotAndStatus(accountId, AccountEntity.Role.ADMIN, AccountEntity.Status.ACTIVE).orElseThrow();
    }

    @Transactional
    public AccountEntity findBlockedNotAdmin(final UUID accountId) {
        return accountRepository.findByIdAndRoleNotAndStatus(accountId, AccountEntity.Role.ADMIN, AccountEntity.Status.BLOCKED).orElseThrow();
    }

    @Transactional
    public AccountEntity findActiveAny(final UUID accountId) {
        return accountRepository.findById(accountId).orElseThrow();
    }
}
