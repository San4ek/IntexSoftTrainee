package me.inquis1tor.userservice.controllers;

import lombok.RequiredArgsConstructor;
import me.inquis1tor.userservice.controllers.operations.PersonalInfoOperations;
import me.inquis1tor.userservice.dtos.PersonalInfoDto;
import me.inquis1tor.userservice.entities.PersonalInfo;
import me.inquis1tor.userservice.mappers.PersonalInfoMapper;
import me.inquis1tor.userservice.services.PersonalInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequestMapping("/api/personalinfo")
@RequiredArgsConstructor
public class PersonalInfoController implements PersonalInfoOperations {

    private final PersonalInfoService personalInfoService;
    private final PersonalInfoMapper personalInfoMapper;

    @Override
    public PersonalInfoDto update(UUID accountId, PersonalInfoDto personalInfoDto) {
        PersonalInfo personalInfo = personalInfoService.update(accountId, personalInfoMapper.dtoToPersonalInfo(personalInfoDto));

        return personalInfoMapper.personalInfoToDto(personalInfo);
    }
}
