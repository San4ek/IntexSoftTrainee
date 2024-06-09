package me.inquis1tor.userservice.mappers;

import me.inquis1tor.userservice.DTOs.AccountAuthDto;
import me.inquis1tor.userservice.entities.Account;
import org.mapstruct.Mapper;

@Mapper
public interface AccountAuthMapper {
    AccountAuthDto accountToAuthDto(Account account);
    Account authDtoToAccount(AccountAuthDto accountAuthDto);
}
