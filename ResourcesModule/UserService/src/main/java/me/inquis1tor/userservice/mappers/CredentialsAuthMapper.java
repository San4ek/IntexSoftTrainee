package me.inquis1tor.userservice.mappers;

import me.inquis1tor.userservice.dtos.CredentialsAuthDto;
import me.inquis1tor.userservice.entities.Credentials;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CredentialsAuthMapper {

    CredentialsAuthDto credentialsToAuthDto(Credentials credentials);

    Credentials authDtoToCredentials(CredentialsAuthDto credentialsAuthDto);
}
