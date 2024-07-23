package me.inquis1tor.userservice.providers.impl;

import me.inquis1tor.userservice.dtos.AccountTransferDto;
import me.inquis1tor.userservice.entities.AccountEntity;
import me.inquis1tor.userservice.providers.DtoProvider;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AccountTransferDtoProvider implements DtoProvider<AccountTransferDto> {

    @Override
    public AccountTransferDto correctDto() {
        return new AccountTransferDto(UUID.fromString("c0a80065-90a2-1cb0-8190-a20de91f0000"), "test@test.ru", AccountEntity.Role.USER);
    }

    @Override
    public AccountTransferDto incorrectDto() {
        return new AccountTransferDto(null, "test.test.ru", null);
    }
}
