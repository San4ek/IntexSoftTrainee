package me.inquis1tor.userservice.controllers.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.inquis1tor.userservice.controllers.PersonalInfosController;
import me.inquis1tor.userservice.dtos.PersonalInfoDto;
import me.inquis1tor.userservice.services.PersonalInfoService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Personal info", description = "Personal info management APIs")
public class PersonalInfosControllerImpl implements PersonalInfosController {

    private final PersonalInfoService personalInfoService;

    @Override
    public void updatePersonalInfo(final PersonalInfoDto personalInfoDto) {
        personalInfoService.updatePersonalInfo(personalInfoDto);
    }
}
