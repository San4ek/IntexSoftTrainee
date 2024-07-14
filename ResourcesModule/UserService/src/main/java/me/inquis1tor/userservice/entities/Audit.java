package me.inquis1tor.userservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * An entity for the {@link AccountEntity} auditing.
 *
 * @author ALexader Sankevich
 */
@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class Audit {

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "deleted_at")
    private LocalDateTime deletedDate;

    @Column(name = "blocked_at")
    private LocalDateTime blockedDate;

    @Column(name = "blocked_by")
    private UUID blockedBy;
}
