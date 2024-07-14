package me.inquis1tor.userservice.mappers;

import me.inquis1tor.userservice.dtos.AccountResponseDto;
import me.inquis1tor.userservice.dtos.AccountTransferDto;
import me.inquis1tor.userservice.entities.AccountEntity;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AccountMapper {

    AccountResponseDto accountToDto(AccountEntity accountEntity);

    AccountEntity dtoToAccount(AccountResponseDto accountResponseDto);

    List<AccountResponseDto> accountListToDtoList(List<AccountEntity> employees);

    AccountEntity transferDtoToAccount(AccountTransferDto accountTransferDto);
}
