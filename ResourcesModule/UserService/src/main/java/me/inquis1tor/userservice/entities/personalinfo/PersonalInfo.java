package me.inquis1tor.userservice.entities.personalinfo;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.inquis1tor.userservice.entities.account.Account;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class PersonalInfo implements Initials {

    @Id
    @Column(name = "account_id")
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "account_id")
    private Account account;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getPatronymic() {
        return null;
    }

    @Override
    public String getSurname() {
        return null;
    }
}
