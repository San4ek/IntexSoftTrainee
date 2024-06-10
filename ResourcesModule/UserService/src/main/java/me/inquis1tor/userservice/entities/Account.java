package me.inquis1tor.userservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "account")
@SQLDelete(sql = "update account set deleted_at=now(),status='DELETED' where id = ?")
public class Account extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @OneToOne(mappedBy = "account", cascade = CascadeType.REMOVE, optional = false)
    @PrimaryKeyJoinColumn
    private Credentials credentials;

    @OneToOne(mappedBy = "account", cascade = CascadeType.REMOVE, optional = false)
    @PrimaryKeyJoinColumn
    private PersonalInfo personalInfo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Override
    public void block() {
        this.setStatus(Status.BLOCKED);
        super.block();
    }

    @Override
    public void unblock() {
        this.setStatus(Status.ACTIVE);
        super.unblock();
    }

    public enum Status {
        ACTIVE,
        BLOCKED,
        DELETED
    }
}
