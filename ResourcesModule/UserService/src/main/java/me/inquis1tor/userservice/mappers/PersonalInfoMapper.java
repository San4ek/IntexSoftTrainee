package me.inquis1tor.userservice.mappers;

import me.inquis1tor.userservice.DTOs.PersonalInfoDto;
import me.inquis1tor.userservice.entities.PersonalInfo;
import org.mapstruct.Mapper;

@Mapper
public interface PersonalInfoMapper {
    PersonalInfoDto personalInfoToDto(PersonalInfo personalInfo);
    PersonalInfo dtoToPersonalInfo(PersonalInfoDto personalInfoDto);
}
