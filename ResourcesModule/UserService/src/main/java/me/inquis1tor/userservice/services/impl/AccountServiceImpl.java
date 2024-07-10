package me.inquis1tor.userservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.inquis1tor.userservice.entities.Account;
import me.inquis1tor.userservice.entities.PersonalInfo;
import me.inquis1tor.userservice.repositories.AccountRepository;
import me.inquis1tor.userservice.services.AccountFinderService;
import me.inquis1tor.userservice.services.AccountService;
import me.inquis1tor.userservice.utils.LoggedAccountHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final LoggedAccountHolder loggedAccountHolder;
    private final AccountFinderService accountFinderService;

    @Override
    @Transactional
    public boolean existsByIdAndStatusAndRoles(UUID id, Account.Status status, Account.Role[] role) {
        return accountRepository.existsByIdAndStatusAndRoleIn(id, status, List.of(role));
    }

    @Override
    @Transactional
    public void createAccount(Account account) {
        log.info("Creating account '{}'", account.getId());

        PersonalInfo personalInfo=new PersonalInfo();
        personalInfo.setId(account.getId());
        account.setStatus(Account.Status.ACTIVE);
        account.setPersonalInfo(personalInfo);
        accountRepository.saveAndFlush(account);

        log.info("Account '{}' created", account.getId());
    }

    @Override
    @Transactional
    public Account getAccount() {
        log.info("User '{}' requested account info", loggedAccountHolder.getId());

        return accountRepository.findById(loggedAccountHolder.getId()).orElseThrow();
    }

    @Override
    @Transactional
    public List<Account> getAll() {
        log.info("User '{}' requested all accounts info", loggedAccountHolder.getId());

        return accountRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(final UUID accountId) {
        log.info("Deleting account '{}'", accountId);

        Account account=accountFinderService.findActiveAny(accountId);
        account.setStatus(Account.Status.DELETED);
        account.setDeletedDate(LocalDateTime.now());
        accountRepository.saveAndFlush(account);

        log.info("Account '{}' deleted", accountId);
    }

    @Override
    @Transactional
    public void block(final UUID accountId, final UUID adminId) {
        log.info("Blocking account '{}' by '{}'", accountId, adminId);

        Account account=accountFinderService.findActiveNotAdmin(accountId);
        account.setStatus(Account.Status.BLOCKED);
        account.setBlockedBy(adminId);
        account.setBlockedDate(LocalDateTime.now());
        accountRepository.saveAndFlush(account);

        log.info("Account '{}' blocked", accountId);
    }

    @Override
    @Transactional
    public void unblock(final UUID accountId) {
        log.info("Unblocking account '{}'", accountId);

        Account account=accountFinderService.findBlockedNotAdmin(accountId);
        account.setStatus(Account.Status.ACTIVE);
        account.setBlockedBy(null);
        account.setBlockedDate(null);
        accountRepository.saveAndFlush(account);

        log.info("Account '{}' unblocked", accountId);
    }

    @Override
    @Transactional
    public boolean existByEmail(final String email) {
        return accountRepository.existsByEmail(email);
    }
}
