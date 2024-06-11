package me.inquis1tor.userservice.controllers;

import lombok.AllArgsConstructor;
import me.inquis1tor.userservice.controllers.operations.AccountOperations;
import me.inquis1tor.userservice.dtos.CredentialsAuthDto;
import me.inquis1tor.userservice.mappers.CredentialsAuthMapper;
import me.inquis1tor.userservice.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
public class AccountController implements AccountOperations {

    private AccountService accountService;
    private CredentialsAuthMapper credentialsAuthMapper;

    @Override
    public ResponseEntity<Void> register(CredentialsAuthDto credentials) {
        accountService.createAccount(credentialsAuthMapper.authDtoToCredentials(credentials));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
