package me.inquis1tor.userservice.services;

import lombok.AllArgsConstructor;
import me.inquis1tor.userservice.entities.Account;
import me.inquis1tor.userservice.entities.Credentials;
import me.inquis1tor.userservice.entities.PersonalInfo;
import me.inquis1tor.userservice.repositories.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private PersonalInfoService personalInfoService;
    private AccountRepository accountRepository;

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

        PersonalInfo personalInfo=new PersonalInfo();
        personalInfo.setAccount(account);

        personalInfoService.save(personalInfo);
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
    public void block(UUID accountId, UUID adminId) {
        accountRepository.blockById(accountId,adminId);
    }

    @Override
    @Transactional
    public void unblock(UUID accountId, UUID adminId) {
        accountRepository.unblockById(accountId);
    }
}
