package me.inquis1tor.userservice.mappers;

import me.inquis1tor.userservice.dtos.CredentialsDto;
import me.inquis1tor.userservice.entities.Credentials;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CredentialsMapper {
    CredentialsDto credentialsToDto(Credentials credentials);
    Credentials dtoToCredentials(CredentialsDto credentialsDto);
}
