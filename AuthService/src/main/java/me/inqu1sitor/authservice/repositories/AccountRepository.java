package me.inqu1sitor.authservice.repositories;

import me.inqu1sitor.authservice.entities.AccountEntity;
import me.inqu1sitor.authservice.entities.AccountRole;
import me.inqu1sitor.authservice.entities.AccountStatus;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends BaseRepository<AccountEntity, UUID> {

    Optional<AccountEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<AccountEntity> findByIdAndStatus(UUID id, AccountStatus status);

    Optional<AccountEntity> findByIdAndRoleNotAndStatus(UUID accountId, AccountRole role, AccountStatus status);
}
