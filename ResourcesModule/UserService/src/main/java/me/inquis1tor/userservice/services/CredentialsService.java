package me.inquis1tor.userservice.services;

import lombok.RequiredArgsConstructor;
import me.inquis1tor.userservice.annotations.ActiveAccountUuid;
import me.inquis1tor.userservice.annotations.UniqueCredentials;
import me.inquis1tor.userservice.entities.Credentials;
import me.inquis1tor.userservice.repositories.CredentialsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
@Service
@RequiredArgsConstructor
public class CredentialsService {

    private final CredentialsRepository credentialsRepository;

    @Transactional
    public boolean existsByEmail(Credentials credentials) {
        return credentialsRepository.existsByEmail(credentials.getEmail());
    }

    //перед сохранением оно делает select данных по id, обновляет и сохраняет
    @Transactional
    public void update(@ActiveAccountUuid UUID accountId,
                       @UniqueCredentials Credentials credentials) {
        credentials.setId(accountId);
        credentialsRepository.saveAndFlush(credentials);
    }

}
