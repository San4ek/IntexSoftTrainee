package me.inquis1tor.userservice.mappers;

import me.inquis1tor.userservice.dtos.AccountResponseDto;
import me.inquis1tor.userservice.entities.Account;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AccountMapper {

    AccountResponseDto accountToDto(Account account);

    Account dtoToAccount(AccountResponseDto accountResponseDto);

    List<AccountResponseDto> accountListToDtoList(List<Account> employees);
}
