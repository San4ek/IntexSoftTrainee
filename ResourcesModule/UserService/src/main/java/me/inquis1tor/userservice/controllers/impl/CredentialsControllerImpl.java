package me.inquis1tor.userservice.controllers.impl;

import lombok.RequiredArgsConstructor;
import me.inquis1tor.userservice.controllers.CredentialsController;
import me.inquis1tor.userservice.dtos.CredentialsAuthDto;
import me.inquis1tor.userservice.mappers.CredentialsAuthMapper;
import me.inquis1tor.userservice.services.impl.CredentialsServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/credentials")
@RequiredArgsConstructor
public class CredentialsControllerImpl implements CredentialsController {

    private final CredentialsServiceImpl credentialsServiceImpl;
    private final CredentialsAuthMapper credentialsAuthMapper;

    @Override
    public void updateCredentials(UUID accountId, CredentialsAuthDto credentials) {
        credentialsServiceImpl.update(accountId, credentialsAuthMapper.authDtoToCredentials(credentials));
    }
}
