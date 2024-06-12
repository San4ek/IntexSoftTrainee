package me.inquis1tor.userservice.repositories;

import me.inquis1tor.userservice.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findById(UUID accountId);
    Optional<Account> findByCredentials_Email(String email);
}
