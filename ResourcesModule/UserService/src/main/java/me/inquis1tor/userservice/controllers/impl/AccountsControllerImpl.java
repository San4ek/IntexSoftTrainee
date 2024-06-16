package me.inquis1tor.userservice.controllers.impl;

import lombok.RequiredArgsConstructor;
import me.inquis1tor.userservice.controllers.AccountsController;
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
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountsControllerImpl implements AccountsController {

    private final AccountService accountService;
    private final CredentialsAuthMapper credentialsAuthMapper;
    private final AccountMapper accountMapper;
    private final AccountAuthMapper accountAuthMapper;

    @Override
    public AccountAuthDto getAccount(String email) {
        return accountAuthMapper.accountToAuthDto(accountService.get(email));
    }

    @Override
    public AccountDto getAccount(UUID accountId) {
        return accountMapper.accountToDto(accountService.get(accountId));
    }

    @Override
    public void registerAccount(CredentialsAuthDto credentials) {
        accountService.createAccount(credentialsAuthMapper.authDtoToCredentials(credentials));
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        return accountMapper.accountListToDtoList(accountService.getAll());
    }

    @Override
    public void deleteAccount(UUID accountId) {
        accountService.delete(accountId);
    }

    @Override
    public AccountDto blockAccount(UUID accountId, UUID adminId) {
        return accountMapper.accountToDto(accountService.block(accountId, adminId));
    }

    @Override
    public AccountDto unblockAccount(UUID accountId, UUID adminId) {
        return accountMapper.accountToDto(accountService.unblock(accountId, adminId));
    }
}
