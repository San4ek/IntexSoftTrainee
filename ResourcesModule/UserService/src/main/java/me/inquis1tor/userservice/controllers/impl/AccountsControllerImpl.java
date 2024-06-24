package me.inquis1tor.userservice.controllers.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.inquis1tor.userservice.controllers.AccountsController;
import me.inquis1tor.userservice.dtos.AccountDetailsTransferDto;
import me.inquis1tor.userservice.dtos.AccountResponseDto;
import me.inquis1tor.userservice.mappers.AccountAuthMapper;
import me.inquis1tor.userservice.mappers.AccountMapper;
import me.inquis1tor.userservice.mappers.CredentialsAuthMapper;
import me.inquis1tor.userservice.services.AccountService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountsControllerImpl implements AccountsController {

    private final AccountService accountService;
    private final CredentialsAuthMapper credentialsAuthMapper;
    private final AccountMapper accountMapper;
    private final AccountAuthMapper accountAuthMapper;

    @Override
    public AccountResponseDto getAccount() {
        log.info("Get account");
        return null;//accountMapper.accountToDto(accountService.getAccount(accountId));
    }

    @Override
    public void registerAccount(AccountDetailsTransferDto account) {
        log.info("Register account");
        //accountService.createAccount(credentialsAuthMapper.authDtoToCredentials(credentials));
    }

    @Override
    public List<AccountResponseDto> getAllAccounts() {
        log.info("Get all accounts");
        return accountMapper.accountListToDtoList(accountService.getAll());
    }

    @Override
    public AccountResponseDto blockAccount(UUID accountId) {
        log.info("Block account");
        return null;//accountMapper.accountToDto(accountService.block(accountId));
    }

    @Override
    public AccountResponseDto unblockAccount(UUID accountId) {
        log.info("Unblock account");
        return null;//accountMapper.accountToDto(accountService.unblock(accountId, adminId));
    }
}
