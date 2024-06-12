package me.inquis1tor.userservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "account")
@SQLDelete(sql = "update account set deleted_at=now(), status='DELETED' where credentials_id = ?")
public class Account extends Audit {

    @Id
    @Column(name = "credentials_id")
    private UUID id;

    @OneToOne(optional = false)
    @MapsId
    @JoinColumn(name = "credentials_id", nullable = false, unique = true)
    private Credentials credentials;

    @OneToOne(mappedBy = "account", cascade = CascadeType.REMOVE, optional = false)
    @PrimaryKeyJoinColumn
    private PersonalInfo personalInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public enum Status {
        ACTIVE,
        BLOCKED,
        DELETED
    }

    public enum Role {
        USER,
        ADMIN,
        MODER
    }
}
