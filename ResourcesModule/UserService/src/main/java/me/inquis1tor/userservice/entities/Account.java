package me.inquis1tor.userservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "account")
public class Account extends Audit {

    @Id
    @Column(name = "credentials_id")
    private UUID id;

    @OneToOne(optional = false)
    @MapsId
    @JoinColumn(name = "credentials_id", nullable = false, unique = true)
    private Credentials credentials;

    @OneToOne(mappedBy = "account", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
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
