package me.inquis1tor.userservice.controllers.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.inquis1tor.userservice.controllers.AccountsController;
import me.inquis1tor.userservice.dtos.AccountResponseDto;
import me.inquis1tor.userservice.dtos.AccountTransferDto;
import me.inquis1tor.userservice.dtos.CredentialsTransferDto;
import me.inquis1tor.userservice.services.AccountService;
import me.inquis1tor.userservice.utils.LoggedAccountDetailsHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Accounts", description = "Account management APIs")
public class AccountsControllerImpl implements AccountsController {

    private final AccountService accountService;
    private final LoggedAccountDetailsHolder loggedAccountDetailsHolder;

    @Override
    public AccountResponseDto getAccount() {
        log.info("Received '{}' request for getting account info", loggedAccountDetailsHolder.getAccountId());
        return accountService.getAccount();
    }

    @Override
    public void registerAccount(final AccountTransferDto dto) {
        log.info("Received '{}' request for new {} registration", dto.id(), dto.role());
        accountService.createAccount(dto);
    }

    @Override
    public List<AccountResponseDto> getAllAccounts() {
        log.info("Received '{}' request for getting all accounts info", loggedAccountDetailsHolder.getAccountId());
        return accountService.getAll();
    }

    @Override
    public void blockAccount(final UUID accountId, final UUID adminId) {
        log.info("Received '{}' request for blocking account '{}'", adminId, accountId);
        accountService.blockAccount(accountId, adminId);
    }

    @Override
    public void unblockAccount(final UUID accountId) {
        log.info("Received request for unblocking account '{}'", accountId);
        accountService.unblockAccount(accountId);
    }

    @Override
    public void updateCredentials(final CredentialsTransferDto dto) {
        log.info("Received '{}' request for updating credentials", dto.id());
        accountService.updateCredentials(dto);
    }
}
