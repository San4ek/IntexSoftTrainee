package me.inquis1tor.userservice.services;

import lombok.AllArgsConstructor;
import me.inquis1tor.userservice.entities.PersonalInfo;
import me.inquis1tor.userservice.repositories.PersonalInfoRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonalInfoService {

    private PersonalInfoRepository personalInfoRepository;

    public PersonalInfo save(PersonalInfo personalInfo) {
        return personalInfoRepository.save(personalInfo);
    }

}
