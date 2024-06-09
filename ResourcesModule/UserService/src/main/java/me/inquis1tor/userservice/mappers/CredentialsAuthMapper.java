package me.inquis1tor.userservice.mappers;

import me.inquis1tor.userservice.DTOs.CredentialsAuthDto;
import me.inquis1tor.userservice.entities.Credentials;
import org.mapstruct.Mapper;

@Mapper
public interface CredentialsAuthMapper {
    CredentialsAuthDto credentialsToAuthDto(Credentials credentials);
    Credentials authDtoToCredentials(CredentialsAuthDto credentialsAuthDto);

}
