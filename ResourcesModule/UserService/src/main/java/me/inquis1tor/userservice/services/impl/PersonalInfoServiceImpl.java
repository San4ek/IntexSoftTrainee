package me.inquis1tor.userservice.services.impl;

import lombok.AllArgsConstructor;
import me.inquis1tor.userservice.entities.PersonalInfo;
import me.inquis1tor.userservice.repositories.PersonalInfoRepository;
import me.inquis1tor.userservice.services.PersonalInfoService;
import me.inquis1tor.userservice.utils.LoggedAccountHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PersonalInfoServiceImpl implements PersonalInfoService {

    private final PersonalInfoRepository personalInfoRepository;
    private final LoggedAccountHolder loggedAccountHolder;

    @Override
    @Transactional
    public PersonalInfo updatePersonalInfo(PersonalInfo personalInfo) {
        personalInfo.setId(loggedAccountHolder.getId());
        return personalInfoRepository.save(personalInfo);
    }

    @Override
    @Transactional
    public PersonalInfo getPersonalInfo() {
        return personalInfoRepository.findById(loggedAccountHolder.getId()).orElseThrow();
    }
}
