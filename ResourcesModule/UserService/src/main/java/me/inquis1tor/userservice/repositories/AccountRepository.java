package me.inquis1tor.userservice.repositories;

import me.inquis1tor.userservice.entities.AccountEntity;
import me.inquis1tor.userservice.entities.AccountRole;
import me.inquis1tor.userservice.entities.AccountStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends BaseRepository<AccountEntity, UUID> {

    boolean existsById(UUID accountId);

    boolean existsByIdAndStatusAndRoleIn(UUID id, AccountStatus status, List<AccountRole> role);

    Optional<AccountEntity> findByIdAndRoleNotAndStatus(UUID accountId, AccountRole role, AccountStatus status);

    boolean existsByEmail(String email);

    AccountEntity findByEmail(String email);

    Optional<AccountEntity> findByIdAndStatus(UUID accountId, AccountStatus status);
}

