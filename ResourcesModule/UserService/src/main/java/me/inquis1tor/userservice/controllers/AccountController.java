package me.inquis1tor.userservice.controllers;

import lombok.RequiredArgsConstructor;
import me.inquis1tor.userservice.configs.AdminUtil;
import me.inquis1tor.userservice.controllers.operations.AccountOperations;
import me.inquis1tor.userservice.dtos.AccountAuthDto;
import me.inquis1tor.userservice.dtos.AccountDto;
import me.inquis1tor.userservice.dtos.CredentialsAuthDto;
import me.inquis1tor.userservice.mappers.AccountAuthMapper;
import me.inquis1tor.userservice.mappers.AccountMapper;
import me.inquis1tor.userservice.mappers.CredentialsAuthMapper;
import me.inquis1tor.userservice.services.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController implements AccountOperations {

    private final AdminUtil adminUtil;
    private final AccountService accountService;
    private final CredentialsAuthMapper credentialsAuthMapper;
    private final AccountMapper accountMapper;
    private final AccountAuthMapper accountAuthMapper;

    @Override
    public AccountAuthDto get(String email) {
        return accountAuthMapper.accountToAuthDto(accountService.get(email));
    }

    @Override
    public AccountDto get(UUID accountId) {
        return accountMapper.accountToDto(accountService.get(accountId));
    }

    @Override
    public void register(CredentialsAuthDto credentials) {
        accountService.createAccount(credentialsAuthMapper.authDtoToCredentials(credentials));
    }

    @Override
    public List<AccountDto> getAll() {
        return accountMapper.accountListToDtoList(accountService.getAll());
    }

    @Override
    public void delete(UUID accountId) {
        accountService.delete(accountId);
    }

    @Override
    public void block(UUID accountId) {
        accountService.block(accountId, adminUtil.getAdminId());
    }

    @Override
    public void unblock(UUID accountId) {
        accountService.unblock(accountId, adminUtil.getAdminId());
    }
}
