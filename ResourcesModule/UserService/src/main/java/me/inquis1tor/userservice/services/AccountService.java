package me.inquis1tor.userservice.services;

import lombok.AllArgsConstructor;
import me.inquis1tor.userservice.annotations.*;
import me.inquis1tor.userservice.entities.Account;
import me.inquis1tor.userservice.entities.Credentials;
import me.inquis1tor.userservice.entities.PersonalInfo;
import me.inquis1tor.userservice.repositories.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Service
@Validated
@AllArgsConstructor
public class AccountService {

    private PersonalInfoService personalInfoService;
    private AccountRepository accountRepository;

    @Transactional
    public boolean existsByIdAndStatusAndRole(UUID id, Account.Status status, Account.Role[] role) {
        System.out.println("work");
        return accountRepository.existsByIdAndStatusAndRoleIn(id,status,List.of(role));
    }

    //work all
    @Transactional
    public void createAccount(@UniqueCredentials Credentials credentials) {
        Account account=new Account();

        account.setRole(Account.Role.USER);
        account.setStatus(Account.Status.ACTIVE);
        account.setCredentials(credentials);

        PersonalInfo personalInfo=new PersonalInfo();
        personalInfo.setAccount(account);

        personalInfoService.save(personalInfo);
    }

    @Transactional
    public Account get(UUID accountId) {
        return accountRepository.findById(accountId).orElseThrow();
    }

    @Transactional
    public Account get(String email) {
        return accountRepository.findByCredentials_Email(email).orElseThrow();
    }

    @Transactional
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Transactional
    public void delete(@ActiveAccountUuid UUID accountId) {
        accountRepository.deleteById(accountId);
    }

    @Transactional
    public void block(@ActiveAccountUuid UUID accountId,
                      @ActiveAdminUuid UUID adminId) {
        accountRepository.blockById(accountId,adminId);
    }

    @Transactional
    public void unblock(@BlockedAccountUuid UUID accountId,
                        @ActiveAdminUuid UUID adminId) {
        accountRepository.unblockById(accountId);
    }
}
