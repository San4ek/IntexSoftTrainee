package me.inquis1tor.userservice.services.impl;

import lombok.RequiredArgsConstructor;
import me.inquis1tor.userservice.entities.Credentials;
import me.inquis1tor.userservice.repositories.CredentialsRepository;
import me.inquis1tor.userservice.services.CredentialsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
@Service
@RequiredArgsConstructor
public class CredentialsServiceImpl implements CredentialsService {

    private final CredentialsRepository credentialsRepository;

    @Override
    @Transactional
    public boolean existsByEmail(Credentials credentials) {
        return credentialsRepository.existsByEmail(credentials.getEmail());
    }

    @Override
    @Transactional
    public void update(UUID accountId, Credentials credentials) {
        credentials.setId(accountId);
        credentialsRepository.saveAndFlush(credentials);
    }
}
