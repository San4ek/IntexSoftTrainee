package me.inqu1sitor.authservice.controllers.impl;

import lombok.RequiredArgsConstructor;
import me.inqu1sitor.authservice.controllers.AccountController;
import me.inqu1sitor.authservice.dtos.CredentialsRequestDto;
import me.inqu1sitor.authservice.entities.Account;
import me.inqu1sitor.authservice.services.AccountService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountControllerImpl implements AccountController {

    private final AccountService accountService;

    @Override
    public void registerUser(CredentialsRequestDto credentialsRequestDto) {
        accountService.createAccount(credentialsRequestDto, Account.Role.USER);
    }

    @Override
    public void registerModer(CredentialsRequestDto credentialsRequestDto) {
        accountService.createAccount(credentialsRequestDto, Account.Role.MODER);
    }

    @Override
    public void registerAdmin(CredentialsRequestDto credentialsRequestDto) {
        accountService.createAccount(credentialsRequestDto, Account.Role.ADMIN);
    }

    @Override
    public void delete() {
        System.out.println("Work");
    }
}
