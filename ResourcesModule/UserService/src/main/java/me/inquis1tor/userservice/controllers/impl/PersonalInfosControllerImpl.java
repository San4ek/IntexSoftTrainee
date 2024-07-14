package me.inquis1tor.userservice.controllers.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.inquis1tor.userservice.controllers.PersonalInfosController;
import me.inquis1tor.userservice.dtos.PersonalInfoDto;
import me.inquis1tor.userservice.services.impl.PersonalInfoServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/personal-infos")
@RequiredArgsConstructor
@Tag(name = "Personal info", description = "Personal info management APIs")
public class PersonalInfosControllerImpl implements PersonalInfosController {

    private final PersonalInfoServiceImpl personalInfoServiceImpl;

    @Override
    public void updatePersonalInfo(final PersonalInfoDto personalInfoDto) {
        personalInfoServiceImpl.updatePersonalInfo(personalInfoDto);
    }
}
