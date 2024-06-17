package me.inquis1tor.userservice.controllers.impl;

import lombok.RequiredArgsConstructor;
import me.inquis1tor.userservice.controllers.AccountsController;
import me.inquis1tor.userservice.dtos.AccountAuthResponseDto;
import me.inquis1tor.userservice.dtos.AccountResponseDto;
import me.inquis1tor.userservice.dtos.CredentialsRequestDto;
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
    public AccountAuthResponseDto getAccount(String email) {
        return accountAuthMapper.accountToAuthDto(accountService.getAccount(email));
    }

    @Override
    public AccountResponseDto getAccount(UUID accountId) {
        return accountMapper.accountToDto(accountService.getAccount(accountId));
    }

    @Override
    public void registerAccount(CredentialsRequestDto credentials) {
        accountService.createAccount(credentialsAuthMapper.authDtoToCredentials(credentials));
    }

    @Override
    public List<AccountResponseDto> getAllAccounts() {
        return accountMapper.accountListToDtoList(accountService.getAll());
    }

    @Override
    public void deleteAccount(UUID accountId) {
        accountService.delete(accountId);
    }

    @Override
    public AccountResponseDto blockAccount(UUID accountId, UUID adminId) {
        return accountMapper.accountToDto(accountService.block(accountId, adminId));
    }

    @Override
    public AccountResponseDto unblockAccount(UUID accountId, UUID adminId) {
        return accountMapper.accountToDto(accountService.unblock(accountId, adminId));
    }
}
