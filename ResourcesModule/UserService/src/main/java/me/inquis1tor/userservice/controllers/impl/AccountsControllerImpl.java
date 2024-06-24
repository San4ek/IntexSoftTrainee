package me.inquis1tor.userservice.controllers.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.inquis1tor.userservice.controllers.AccountsController;
import me.inquis1tor.userservice.dtos.AccountDetailsTransferDto;
import me.inquis1tor.userservice.dtos.AccountResponseDto;
import me.inquis1tor.userservice.mappers.AccountMapper;
import me.inquis1tor.userservice.mappers.TransferAccountMapper;
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
    private final TransferAccountMapper transferAccountMapper;
    private final AccountMapper accountMapper;

    @Override
    public AccountResponseDto getAccount() {
        return accountMapper.accountToDto(accountService.getAccount());
    }

    @Override
    public void registerAccount(AccountDetailsTransferDto transferDto) {
        accountService.createAccount(transferAccountMapper.transferDtoToAccount(transferDto));
    }

    @Override
    public List<AccountResponseDto> getAllAccounts() {
        return accountMapper.accountListToDtoList(accountService.getAll());
    }

    @Override
    public void blockAccount(UUID accountId, UUID adminId) {
        accountService.block(accountId, adminId);
    }

    @Override
    public void unblockAccount(UUID accountId) {
        accountService.unblock(accountId);
    }
}
