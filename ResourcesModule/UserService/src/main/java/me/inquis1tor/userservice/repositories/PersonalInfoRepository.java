package me.inquis1tor.userservice.repositories;

import me.inquis1tor.userservice.entities.PersonalInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonalInfoRepository extends JpaRepository<PersonalInfoEntity, UUID> {

}
