package me.inquis1tor.userservice.entities.role;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.inquis1tor.userservice.entities.account.Account;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Role implements Title {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private UUID id;

    @OneToMany(mappedBy = "role")
    private List<Account> accounts;

    @Override
    public String getTitle() {
        return null;
    }
}
