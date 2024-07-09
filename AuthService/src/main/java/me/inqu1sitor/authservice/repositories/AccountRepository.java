package me.inqu1sitor.authservice.repositories;

import me.inqu1sitor.authservice.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

    Optional<AccountEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<AccountEntity> findByIdAndRoleNotAndStatus(UUID accountId, AccountEntity.Role role, AccountEntity.Status status);
}
