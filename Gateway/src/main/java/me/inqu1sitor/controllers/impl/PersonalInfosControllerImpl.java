package me.inqu1sitor.controllers.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.inqu1sitor.clients.PersonalInfoClient;
import me.inqu1sitor.controllers.PersonalInfosController;
import me.inqu1sitor.dtos.PersonalInfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Personal info", description = "Personal info management APIs")
public class PersonalInfosControllerImpl implements PersonalInfosController {

    private final PersonalInfoClient personalInfoClient;

    @Override
    public ResponseEntity<Object> updatePersonalInfo(PersonalInfoDto personalInfo) {
        return personalInfoClient.updatePersonalInfo(personalInfo);
    }
}
