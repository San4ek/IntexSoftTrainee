package me.inquis1tor.userservice.services;

import lombok.AllArgsConstructor;
import me.inquis1tor.userservice.annotations.AnyActiveUuid;
import me.inquis1tor.userservice.entities.PersonalInfo;
import me.inquis1tor.userservice.repositories.PersonalInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
@Service
@AllArgsConstructor
public class PersonalInfoService {

    private final PersonalInfoRepository personalInfoRepository;

    @Transactional
    public PersonalInfo update(@AnyActiveUuid UUID accountId,
                               PersonalInfo personalInfo) {

        personalInfo.setId(accountId);

        return personalInfoRepository.save(personalInfo);
    }
}
