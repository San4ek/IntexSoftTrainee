package me.inquis1tor.userservice.repositories;

import me.inquis1tor.userservice.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    boolean existsById(UUID id);

    boolean existsByIdAndStatusAndRoleIn(UUID id, Account.Status status, List<Account.Role> role);

    Optional<Account> findByIdAndRoleNotAndStatus(UUID accountId, Account.Role role, Account.Status status);

    boolean existsByEmail(String email);
}

