package me.inqu1sitor.authservice.repositories;

import me.inqu1sitor.authservice.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByIdAndStatusAndRoleIn(UUID id, Account.Status status, Set<Account.Role> roles);

    Optional<Account> findByIdAndRoleNotAndStatus(UUID accountId, Account.Role role, Account.Status status);
}
