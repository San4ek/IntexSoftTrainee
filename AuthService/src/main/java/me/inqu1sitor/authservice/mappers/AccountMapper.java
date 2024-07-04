package me.inqu1sitor.authservice.mappers;

import me.inqu1sitor.authservice.dtos.AccountDetailsTransferDto;
import me.inqu1sitor.authservice.dtos.CredentialsRequestDto;
import me.inqu1sitor.authservice.dtos.CredentialsTransferDto;
import me.inqu1sitor.authservice.entities.Account;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AccountMapper {

    Account credentialsToAccount(CredentialsRequestDto credentials);

    AccountDetailsTransferDto accountToTransferDto(Account account);

    CredentialsTransferDto accountToCredentialsTransferDto(Account account);
}
