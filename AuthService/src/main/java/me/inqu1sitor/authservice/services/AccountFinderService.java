package me.inqu1sitor.authservice.services;

import lombok.RequiredArgsConstructor;
import me.inqu1sitor.authservice.entities.Account;
import me.inqu1sitor.authservice.repositories.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountFinderService {

    private final AccountRepository accountRepository;

    @Transactional
    public Account findActiveNotAdmin(final UUID accountId) {
        return accountRepository.findByIdAndRoleNotAndStatus(accountId, Account.Role.ADMIN, Account.Status.ACTIVE).orElseThrow();
    }

    @Transactional
    public Account findBlockedNotAdmin(final UUID accountId) {
        return accountRepository.findByIdAndRoleNotAndStatus(accountId, Account.Role.ADMIN, Account.Status.BLOCKED).orElseThrow();
    }

    @Transactional
    public Account findActiveAny(final UUID accountId) {
        return accountRepository.findById(accountId).orElseThrow();
    }
}
