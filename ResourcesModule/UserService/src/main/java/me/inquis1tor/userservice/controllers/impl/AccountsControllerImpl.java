package me.inquis1tor.userservice.controllers.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.inquis1tor.userservice.controllers.AccountsController;
import me.inquis1tor.userservice.dtos.AccountDetailsTransferDto;
import me.inquis1tor.userservice.dtos.AccountResponseDto;
import me.inquis1tor.userservice.mappers.AccountMapper;
import me.inquis1tor.userservice.services.AccountService;
import me.inquis1tor.userservice.utils.LoggedAccountHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(name = "Accounts", description = "Account management APIs")
public class AccountsControllerImpl implements AccountsController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final LoggedAccountHolder loggedAccountHolder;

    @Override
    public AccountResponseDto getAccount() {
        log.info("Received '{}' request for getting account info", loggedAccountHolder.getId());

        return accountMapper.accountToDto(accountService.getAccount());
    }

    @Override
    public void registerAccount(AccountDetailsTransferDto transferDto) {
        log.info("Received '{}' request for new {} registration", transferDto.id(), transferDto.role());

        accountService.createAccount(accountMapper.transferDtoToAccount(transferDto));
    }

    @Override
    public List<AccountResponseDto> getAllAccounts() {
        log.info("Received '{}' request for getting all accounts info", loggedAccountHolder.getId());

        return accountMapper.accountListToDtoList(accountService.getAll());
    }

    @Override
    public void blockAccount(UUID accountId, UUID adminId) {
        log.info("Received '{}' request for blocking account '{}'", adminId, accountId);

        accountService.block(accountId, adminId);
    }

    @Override
    public void unblockAccount(UUID accountId) {
        log.info("Received request for unblocking account '{}'", accountId);

        accountService.unblock(accountId);
    }
}
