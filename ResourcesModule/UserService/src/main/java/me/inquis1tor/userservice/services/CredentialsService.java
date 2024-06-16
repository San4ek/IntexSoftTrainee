package me.inquis1tor.userservice.services;

import me.inquis1tor.userservice.annotations.validation.uuid.UserOrModerActiveUuid;
import me.inquis1tor.userservice.annotations.validation.credentials.UniqueCredentials;
import me.inquis1tor.userservice.entities.Credentials;

import java.util.UUID;

public interface CredentialsService {

    boolean existsByEmail(Credentials credentials);

    void update(@UserOrModerActiveUuid UUID accountId, @UniqueCredentials Credentials credentials);
}
