package org.example.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class Audit {

    @Column(name = "createdAt")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "editedAt")
    @LastModifiedDate
    private LocalDateTime editedAt;

    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;

    @Column(name = "createdBy")
    @CreatedBy
    private String createdBy;

    @Column(name = "editedBy")
    @LastModifiedBy
    private String editedBy;

    @Column(name = "deletedBy")
    private String deletedBy;
}
