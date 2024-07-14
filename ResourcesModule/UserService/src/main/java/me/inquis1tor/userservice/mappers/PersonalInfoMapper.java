package me.inquis1tor.userservice.mappers;

import me.inquis1tor.userservice.dtos.PersonalInfoDto;
import me.inquis1tor.userservice.entities.PersonalInfoEntity;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface PersonalInfoMapper {

    PersonalInfoDto personalInfoToDto(PersonalInfoEntity personalInfoEntity);

    PersonalInfoEntity dtoToPersonalInfo(PersonalInfoDto personalInfoDto);
}
