package me.inquis1tor.userservice.services;

import lombok.AllArgsConstructor;
import me.inquis1tor.userservice.entities.Account;
import me.inquis1tor.userservice.entities.Credentials;
import me.inquis1tor.userservice.entities.PersonalInfo;
import me.inquis1tor.userservice.repositories.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AccountService {

    private AccountRepository accountRepository;

    private CredentialsService credentialsService;
    private PersonalInfoService personalInfoService;

    @Transactional
    public void createAccount(Credentials credentials) {
        if (!credentialsService.existsByEmail(credentials)) {
            Account account=new Account();

            account.setRole(Account.Role.USER);
            account.setStatus(Account.Status.ACTIVE);
            account.setCredentials(credentials);

            PersonalInfo personalInfo=new PersonalInfo();
            personalInfo.setAccount(account);

            personalInfoService.save(personalInfo);

        } else throw new RuntimeException();
    }

}
