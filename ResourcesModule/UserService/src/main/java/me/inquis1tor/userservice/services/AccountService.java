package me.inquis1tor.userservice.services;

import lombok.AllArgsConstructor;
import me.inquis1tor.userservice.annotations.UniqueEmail;
import me.inquis1tor.userservice.entities.Account;
import me.inquis1tor.userservice.entities.Credentials;
import me.inquis1tor.userservice.entities.PersonalInfo;
import me.inquis1tor.userservice.repositories.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Service
@Validated
@AllArgsConstructor
public class AccountService {

    private PersonalInfoService personalInfoService;
    private AccountRepository accountRepository;

    @Transactional
    public void createAccount(@UniqueEmail Credentials credentials) {
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
}
