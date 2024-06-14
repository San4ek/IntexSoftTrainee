package me.inquis1tor.userservice.controllers;

import lombok.RequiredArgsConstructor;
import me.inquis1tor.userservice.controllers.operations.CredentialsOperations;
import me.inquis1tor.userservice.dtos.CredentialsAuthDto;
import me.inquis1tor.userservice.mappers.CredentialsAuthMapper;
import me.inquis1tor.userservice.services.CredentialsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/credentials")
@RequiredArgsConstructor
public class CredentialsController implements CredentialsOperations {

    private final CredentialsService credentialsService;
    private final CredentialsAuthMapper credentialsAuthMapper;

    @Override
    public void update(UUID accountId, CredentialsAuthDto credentials) {
        credentialsService.update(accountId, credentialsAuthMapper.authDtoToCredentials(credentials));
    }
}
