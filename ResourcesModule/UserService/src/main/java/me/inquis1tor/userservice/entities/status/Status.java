package me.inquis1tor.userservice.entities.status;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.inquis1tor.userservice.entities.account.Account;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Status implements AccountStatus {

    @Id
    @Column(name = "account_id")
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "account_id")
    private Account account;

    @Override
    public boolean isBLocked() {
        return false;
    }

    @Override
    public boolean isDeleted() {
        return false;
    }
}
