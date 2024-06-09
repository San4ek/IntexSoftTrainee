package me.inquis1tor.userservice.mappers;

import me.inquis1tor.userservice.DTOs.AccountDto;
import me.inquis1tor.userservice.entities.Account;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AccountMapper {
    AccountDto accountToDto(Account account);
    Account dtoToAccount(AccountDto accountDto);
}
