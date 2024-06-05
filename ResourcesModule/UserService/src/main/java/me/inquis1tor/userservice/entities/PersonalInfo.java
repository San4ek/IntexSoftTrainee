package me.inquis1tor.userservice.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class PersonalInfo {

    @Id
    @Column(name = "account_id")
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "account_id")
    private Account account;

    private String name;
    private String surname;
    private String patronymic;

    @Column(unique = true, nullable = false)
    private String phoneNumber;
}
