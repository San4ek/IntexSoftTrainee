package me.inquis1tor.userservice.mappers;

import me.inquis1tor.userservice.dtos.AccountDto;
import me.inquis1tor.userservice.entities.Account;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AccountMapper {
    AccountDto accountToDto(Account account);
    Account dtoToAccount(AccountDto accountDto);

    List<AccountDto> accountListToDtoList(List<Account> employees);
}
