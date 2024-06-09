package me.inquis1tor.userservice.mappers;

import me.inquis1tor.userservice.DTOs.CredentialsDto;
import me.inquis1tor.userservice.entities.Credentials;
import org.mapstruct.Mapper;

@Mapper
public interface CredentialsMapper {
    CredentialsDto credentialsToDto(Credentials credentials);
    Credentials dtoToCredentials(CredentialsDto credentialsDto);
}
