package me.inquis1tor.userservice.mappers;

import me.inquis1tor.userservice.dtos.AccountAuthResponseDto;
import me.inquis1tor.userservice.entities.Account;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AccountAuthMapper {

    AccountAuthResponseDto accountToAuthDto(Account account);

    Account authDtoToAccount(AccountAuthResponseDto accountAuthResponseDto);
}
