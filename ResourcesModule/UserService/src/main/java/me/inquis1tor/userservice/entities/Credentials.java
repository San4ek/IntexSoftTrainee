package me.inquis1tor.userservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(indexes = @Index(columnList = "email", unique = true))
public class Credentials {

    @Id
    @Column(name = "account_id", nullable = false)
    private UUID id;

    @OneToOne(optional = false)
    @MapsId
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
}
