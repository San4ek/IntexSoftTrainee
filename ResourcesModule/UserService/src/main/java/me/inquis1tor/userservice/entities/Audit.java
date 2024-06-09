package me.inquis1tor.userservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class Audit {

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedDate;

    @Column(name = "blocked_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date blockedDate;

    @Column(name = "blocked_by")
    private UUID blockedBy;

    public void block(UUID id) {
        this.setBlockedBy(id);
        this.setBlockedDate(new Date());
    }
}
