package me.inquis1tor.userservice.providers;

import me.inquis1tor.userservice.entities.PersonalInfoEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PersonalInfoEntityProvider {

    public PersonalInfoEntity correctEntity(String accountId) {
        PersonalInfoEntity personalInfoEntity = new PersonalInfoEntity();
        personalInfoEntity.setId(UUID.fromString(accountId));
        return personalInfoEntity;
    }
}
