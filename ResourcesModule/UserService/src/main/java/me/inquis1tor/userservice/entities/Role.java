package me.inquis1tor.userservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Role {

    @Id
    @Column(name = "id")
    private UUID id;

    @OneToMany(mappedBy = "role")
    private List<Account> accounts;

    private String title;
}
