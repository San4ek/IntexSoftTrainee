package me.inquis1tor.userservice.services;

import lombok.AllArgsConstructor;
import me.inquis1tor.userservice.entities.Credentials;
import me.inquis1tor.userservice.repositories.CredentialsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CredentialsService {

    private CredentialsRepository credentialsRepository;

    @Transactional
    public boolean existsByEmail(Credentials credentials) {
        return credentialsRepository.existsByEmail(credentials.getEmail());
    }

    @Transactional
    public Credentials save(Credentials credentials) {
        return credentialsRepository.saveAndFlush(credentials);
    }

}
