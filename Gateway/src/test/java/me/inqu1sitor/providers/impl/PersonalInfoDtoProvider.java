package me.inqu1sitor.providers.impl;

import me.inqu1sitor.dtos.PersonalInfoDto;
import me.inqu1sitor.providers.DtoProvider;
import org.springframework.stereotype.Component;

@Component
public class PersonalInfoDtoProvider implements DtoProvider<PersonalInfoDto> {

    @Override
    public PersonalInfoDto correctDto() {
        return new PersonalInfoDto("test", "test", "test", "+000000000000");
    }
}
