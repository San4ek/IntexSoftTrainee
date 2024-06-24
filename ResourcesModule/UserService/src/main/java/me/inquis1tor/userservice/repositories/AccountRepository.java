package me.inquis1tor.userservice.repositories;

import me.inquis1tor.userservice.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    boolean existsById(UUID id);

    boolean existsByIdAndStatusAndRoleIn(UUID id, Account.Status status, List<Account.Role> role);

    @Modifying
    @Query("update Account set deletedDate=current_timestamp, status='DELETED' where id = ?1")
    void deleteById(UUID accountId);

    @Query(value = "UPDATE account SET status='BLOCKED', blocked_at=current_timestamp, blocked_by=?2 WHERE credentials_id=?1 returning *", nativeQuery = true)
    Account blockById(UUID accountId);

    @Modifying
    @Query(value = "UPDATE account SET status='ACTIVE', blocked_at=null, blocked_by=null WHERE credentials_id=?1", nativeQuery = true)
    Account unblockById(UUID accountId);

    boolean existsByEmail(String email);
}

