package me.inquis1tor.userservice.mappers;

import me.inquis1tor.userservice.dtos.CredentialsResponseDto;
import me.inquis1tor.userservice.entities.Credentials;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CredentialsMapper {

    CredentialsResponseDto credentialsToDto(Credentials credentials);

    Credentials dtoToCredentials(CredentialsResponseDto credentialsResponseDto);
}
