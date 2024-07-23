package me.inquis1tor.userservice.providers.impl;

import me.inquis1tor.userservice.dtos.PersonalInfoDto;
import me.inquis1tor.userservice.providers.DtoProvider;
import org.springframework.stereotype.Component;

@Component
public class PersonalInfoDtoProvider implements DtoProvider<PersonalInfoDto> {

    @Override
    public PersonalInfoDto correctDto() {
        return new PersonalInfoDto("test", "test", "test", "0000000000");
    }

    @Override
    public PersonalInfoDto incorrectDto() {
        return correctDto();
    }
}
