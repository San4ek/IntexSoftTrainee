package me.inquis1tor.userservice.repositories;

import me.inquis1tor.userservice.entities.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CredentialsRepository extends JpaRepository<Credentials, UUID> {

    boolean existsByEmail(String email);
}
