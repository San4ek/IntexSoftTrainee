package me.inquis1tor.userservice.services.impl;

import lombok.RequiredArgsConstructor;
import me.inquis1tor.userservice.entities.Account;
import me.inquis1tor.userservice.entities.PersonalInfo;
import me.inquis1tor.userservice.repositories.AccountRepository;
import me.inquis1tor.userservice.services.AccountService;
import me.inquis1tor.userservice.utils.LoggedAccountHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final LoggedAccountHolder loggedAccountHolder;

    @Override
    @Transactional
    public boolean existsByIdAndStatusAndRoles(UUID id, Account.Status status, Account.Role[] role) {
        return accountRepository.existsByIdAndStatusAndRoleIn(id, status, List.of(role));
    }

    @Override
    @Transactional
    public void createAccount(Account account) {
        PersonalInfo personalInfo=new PersonalInfo();
        personalInfo.setId(account.getId());
        account.setStatus(Account.Status.ACTIVE);
        account.setPersonalInfo(personalInfo);
        accountRepository.saveAndFlush(account);
    }

    @Override
    @Transactional
    public Account getAccount() {
        return accountRepository.findById(loggedAccountHolder.getId()).orElseThrow();
    }

    @Override
    @Transactional
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(final UUID accountId) {
        Account account=new Account();
        account.setId(accountId);
        account.setStatus(Account.Status.DELETED);
        account.setDeletedDate(LocalDateTime.now());
        accountRepository.saveAndFlush(account);
    }

    @Override
    @Transactional
    public void block(final UUID accountId, final UUID adminId) {
        Account account=new Account();
        account.setId(accountId);
        account.setStatus(Account.Status.BLOCKED);
        account.setBlockedBy(adminId);
        account.setBlockedDate(LocalDateTime.now());
        accountRepository.saveAndFlush(account);
    }

    @Override
    @Transactional
    public void unblock(final UUID accountId) {
        Account account=new Account();
        account.setId(accountId);
        account.setStatus(Account.Status.ACTIVE);
        accountRepository.saveAndFlush(account);
    }

    @Override
    @Transactional
    public boolean existByEmail(final String email) {
        return accountRepository.existsByEmail(email);
    }
}
