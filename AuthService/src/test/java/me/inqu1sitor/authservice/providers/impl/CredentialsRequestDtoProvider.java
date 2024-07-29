package me.inqu1sitor.authservice.providers.impl;

import me.inqu1sitor.authservice.dtos.CredentialsRequestDto;
import me.inqu1sitor.authservice.providers.DtoProvider;
import org.springframework.stereotype.Component;

@Component
public class CredentialsRequestDtoProvider implements DtoProvider<CredentialsRequestDto> {
    @Override
    public CredentialsRequestDto correctDto() {
        return new CredentialsRequestDto("test@test.ru", "123456");
    }

    @Override
    public CredentialsRequestDto incorrectDto() {
        return new CredentialsRequestDto("test.test.ru", "12345");
    }
}
