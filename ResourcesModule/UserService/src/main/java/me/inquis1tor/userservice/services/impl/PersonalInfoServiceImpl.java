package me.inquis1tor.userservice.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.inquis1tor.userservice.entities.PersonalInfo;
import me.inquis1tor.userservice.repositories.PersonalInfoRepository;
import me.inquis1tor.userservice.services.PersonalInfoService;
import me.inquis1tor.userservice.utils.LoggedAccountHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class PersonalInfoServiceImpl implements PersonalInfoService {

    private final PersonalInfoRepository personalInfoRepository;
    private final LoggedAccountHolder loggedAccountHolder;

    @Override
    @Transactional
    public void updatePersonalInfo(PersonalInfo personalInfo) {
        log.info("Updating '{}' personal info", loggedAccountHolder.getId());

        personalInfo.setId(loggedAccountHolder.getId());
        personalInfoRepository.save(personalInfo);

        log.info("Personal info '{}' updated", loggedAccountHolder.getId());
    }

    @Override
    @Transactional
    public PersonalInfo getPersonalInfo() {
        log.info("User '{}' requested personal info", loggedAccountHolder.getId());

        return personalInfoRepository.findById(loggedAccountHolder.getId()).orElseThrow();
    }
}
