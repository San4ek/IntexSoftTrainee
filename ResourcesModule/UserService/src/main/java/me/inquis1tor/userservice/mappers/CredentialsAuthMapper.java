package me.inquis1tor.userservice.mappers;

import me.inquis1tor.userservice.dtos.CredentialsRequestDto;
import me.inquis1tor.userservice.entities.Credentials;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CredentialsAuthMapper {

    CredentialsRequestDto credentialsToAuthDto(Credentials credentials);

    Credentials authDtoToCredentials(CredentialsRequestDto credentialsRequestDto);
}
