package me.inquis1tor.userservice.repositories;

import me.inquis1tor.userservice.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByCredentials_Email(String email);
    boolean existsByIdAndStatusAndRoleIn(UUID id, Account.Status status, Account.Role[] role);
    void deleteById(UUID accountId);

    @Modifying
    @Query("UPDATE Account SET status='BLOCKED', blockedDate=current_timestamp , blockedBy=?2 WHERE id=?1")
    void blockById(UUID accountId, UUID adminId);

    @Modifying
    @Query("UPDATE Account SET status='ACTIVE', blockedDate=null, blockedBy=null WHERE credentials.id=?1")
    void unblockById(UUID accountId);
}

