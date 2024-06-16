package me.inquis1tor.userservice.controllers.impl;

import lombok.RequiredArgsConstructor;
import me.inquis1tor.userservice.controllers.PersonalInfosController;
import me.inquis1tor.userservice.dtos.PersonalInfoDto;
import me.inquis1tor.userservice.entities.PersonalInfo;
import me.inquis1tor.userservice.mappers.PersonalInfoMapper;
import me.inquis1tor.userservice.services.impl.PersonalInfoServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/personal-infos")
@RequiredArgsConstructor
public class PersonalInfoControllerImpl implements PersonalInfosController {

    private final PersonalInfoServiceImpl personalInfoServiceImpl;
    private final PersonalInfoMapper personalInfoMapper;

    @Override
    public PersonalInfoDto updatePersonalInfo(UUID accountId, PersonalInfoDto personalInfoDto) {
        PersonalInfo personalInfo = personalInfoServiceImpl.update(accountId, personalInfoMapper.dtoToPersonalInfo(personalInfoDto));

        return personalInfoMapper.personalInfoToDto(personalInfo);
    }
}
