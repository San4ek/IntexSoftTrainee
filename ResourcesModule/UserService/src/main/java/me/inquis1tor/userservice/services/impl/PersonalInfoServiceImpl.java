package me.inquis1tor.userservice.services.impl;

import lombok.AllArgsConstructor;
import me.inquis1tor.userservice.entities.PersonalInfo;
import me.inquis1tor.userservice.repositories.PersonalInfoRepository;
import me.inquis1tor.userservice.services.PersonalInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
@Service
@AllArgsConstructor
public class PersonalInfoServiceImpl implements PersonalInfoService {

    private final PersonalInfoRepository personalInfoRepository;

    @Transactional
    public PersonalInfo update(UUID accountId, PersonalInfo personalInfo) {
        personalInfo.setId(accountId);

        return personalInfoRepository.save(personalInfo);
    }
}
