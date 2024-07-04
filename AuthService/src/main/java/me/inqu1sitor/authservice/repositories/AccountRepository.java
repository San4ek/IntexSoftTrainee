package me.inqu1sitor.authservice.repositories;

import me.inqu1sitor.authservice.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

    Optional<AccountEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByIdAndStatusAndRoleIn(UUID id, AccountEntity.Status status, Set<AccountEntity.Role> roles);

    Optional<AccountEntity> findByIdAndRoleNotAndStatus(UUID accountId, AccountEntity.Role role, AccountEntity.Status status);
}
