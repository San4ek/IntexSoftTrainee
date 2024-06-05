package me.inquis1tor.userservice.entities.account;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.inquis1tor.userservice.entities.credentials.AccountCredentials;
import me.inquis1tor.userservice.entities.credentials.Credentials;
import me.inquis1tor.userservice.entities.personalinfo.Initials;
import me.inquis1tor.userservice.entities.personalinfo.PersonalInfo;
import me.inquis1tor.userservice.entities.role.Role;
import me.inquis1tor.userservice.entities.role.Title;
import me.inquis1tor.userservice.entities.status.AccountStatus;
import me.inquis1tor.userservice.entities.status.Status;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account implements AccountInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private UUID id;

    @OneToOne(mappedBy = "account", cascade = CascadeType.REMOVE)
    @PrimaryKeyJoinColumn
    private Credentials credentials;

    @OneToOne(mappedBy = "account", cascade = CascadeType.REMOVE)
    @PrimaryKeyJoinColumn
    private PersonalInfo personalInfo;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne(mappedBy = "account", cascade = CascadeType.REMOVE)
    @PrimaryKeyJoinColumn
    private Status status;

    @Override
    public AccountCredentials getAccountCredentials() {
        return null;
    }

    @Override
    public Initials getPersonalInfo() {
        return null;
    }

    @Override
    public Title getRoleTitle() {
        return null;
    }

    @Override
    public AccountStatus getAccountStatus() {
        return null;
    }
}
