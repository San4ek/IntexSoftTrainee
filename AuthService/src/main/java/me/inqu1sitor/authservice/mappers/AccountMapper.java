package me.inqu1sitor.authservice.mappers;

import me.inqu1sitor.authservice.dtos.AccountTransferDto;
import me.inqu1sitor.authservice.dtos.CredentialsTransferDto;
import me.inqu1sitor.authservice.entities.AccountEntity;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AccountMapper {

    AccountTransferDto toAccountTransferDto(AccountEntity accountEntity);

    CredentialsTransferDto toCredentialsTransferDto(AccountEntity accountEntity);
}
