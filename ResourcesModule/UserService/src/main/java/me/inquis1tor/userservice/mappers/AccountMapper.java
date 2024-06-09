package me.inquis1tor.userservice.mappers;

import me.inquis1tor.userservice.DTOs.AccountDto;
import me.inquis1tor.userservice.entities.Account;
import org.mapstruct.Mapper;

@Mapper
public interface AccountMapper {
    AccountDto accountToDto(Account account);
    Account dtoToAccount(AccountDto accountDto);
}
