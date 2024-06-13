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
import me.inquis1tor.userservice.services.AccountServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController implements AccountOperations {

    private final AccountServiceImpl accountServiceImpl;
    private final CredentialsAuthMapper credentialsAuthMapper;
    private final AccountMapper accountMapper;
    private final AccountAuthMapper accountAuthMapper;

    @Override
    public AccountAuthDto get(String email) {
        return accountAuthMapper.accountToAuthDto(accountServiceImpl.get(email));
    }

    @Override
    public AccountDto get(UUID accountId) {
        return accountMapper.accountToDto(accountServiceImpl.get(accountId));
    }

    @Override
    public void register(CredentialsAuthDto credentials) {
        accountServiceImpl.createAccount(credentialsAuthMapper.authDtoToCredentials(credentials));
    }

    @Override
    public List<AccountDto> getAll() {
        return accountMapper.accountListToDtoList(accountServiceImpl.getAll());
    }

    @Override
    public void delete(UUID accountId) {
        accountServiceImpl.delete(accountId);
    }

    @Override
    public void block(UUID accountId) {
        accountServiceImpl.block(accountId, AdminUtil.getAdminId());
    }

    @Override
    public void unblock(UUID accountId) {
        accountServiceImpl.unblock(accountId, AdminUtil.getAdminId());
    }
}
