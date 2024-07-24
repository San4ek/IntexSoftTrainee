package me.inquis1tor.userservice.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "account")
public class AccountEntity extends Audit {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "email", nullable = false)
    private String email;

    @OneToOne(mappedBy = "accountEntity", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private PersonalInfoEntity personalInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AccountEntity account = (AccountEntity) object;
        return Objects.equals(getId(), account.getId()) && Objects.equals(getEmail(), account.getEmail()) && getRole() == account.getRole() && getStatus() == account.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getPersonalInfo(), getRole(), getStatus());
    }

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
