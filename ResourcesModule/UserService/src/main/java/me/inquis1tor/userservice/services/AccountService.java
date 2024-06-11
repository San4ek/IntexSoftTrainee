package me.inquis1tor.userservice.services;

import lombok.AllArgsConstructor;
import me.inquis1tor.userservice.annotations.UniqueEmail;
import me.inquis1tor.userservice.entities.Account;
import me.inquis1tor.userservice.entities.Credentials;
import me.inquis1tor.userservice.entities.PersonalInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@AllArgsConstructor
public class AccountService {

    private PersonalInfoService personalInfoService;

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
}
