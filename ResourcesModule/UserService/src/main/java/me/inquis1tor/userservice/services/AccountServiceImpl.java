package me.inquis1tor.userservice.services;

import lombok.RequiredArgsConstructor;
import me.inquis1tor.userservice.entities.Account;
import me.inquis1tor.userservice.entities.Credentials;
import me.inquis1tor.userservice.repositories.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public boolean existsByIdAndStatusAndRoles(UUID id, Account.Status status, Account.Role[] role) {
        return accountRepository.existsByIdAndStatusAndRoleIn(id,status,List.of(role));
    }

    @Override
    @Transactional
    public void createAccount(Credentials credentials) {
        Account account=new Account();

        account.setRole(Account.Role.USER);
        account.setStatus(Account.Status.ACTIVE);
        account.setCredentials(credentials);

        accountRepository.saveAndFlush(account);
    }

    @Override
    @Transactional
    public Account get(UUID accountId) {
        return accountRepository.findById(accountId).orElseThrow();
    }

    @Override
    @Transactional
    public Account get(String email) {
        return accountRepository.findByCredentials_Email(email).orElseThrow();
    }

    @Override
    @Transactional
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(UUID accountId) {
        accountRepository.deleteById(accountId);
    }

    @Override
    @Transactional
    public Account block(UUID accountId, UUID adminId) {
        return accountRepository.blockById(accountId,adminId);
    }

    @Override
    @Transactional
    public Account unblock(UUID accountId, UUID adminId) {
        return accountRepository.unblockById(accountId);
    }
}
