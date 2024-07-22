package me.inquis1tor.userservice.providers.impl;

import me.inquis1tor.userservice.dtos.CredentialsTransferDto;
import me.inquis1tor.userservice.providers.DtoProvider;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CredentialsTransferDtoProvider implements DtoProvider<CredentialsTransferDto> {
    @Override
    public CredentialsTransferDto correctDto() {
        return new CredentialsTransferDto(UUID.fromString("c0a80065-90a2-1cb0-8190-a20de91f0000"), "test@test.ru");
    }

    @Override
    public CredentialsTransferDto incorrectDto() {
        return new CredentialsTransferDto(null, null);
    }
}
