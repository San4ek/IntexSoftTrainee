package me.inquis1tor.userservice.repositories;

import me.inquis1tor.userservice.entities.AccountEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends BaseRepository<AccountEntity, UUID> {

    boolean existsById(UUID accountId);

    boolean existsByIdAndStatusAndRoleIn(UUID id, AccountEntity.Status status, List<AccountEntity.Role> role);

    Optional<AccountEntity> findByIdAndRoleNotAndStatus(UUID accountId, AccountEntity.Role role, AccountEntity.Status status);

    boolean existsByEmail(String email);
}

