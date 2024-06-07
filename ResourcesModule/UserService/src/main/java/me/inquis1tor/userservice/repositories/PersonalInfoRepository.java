package me.inquis1tor.userservice.repositories;

import me.inquis1tor.userservice.entities.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, UUID> {
}
