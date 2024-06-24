package me.inquis1tor.userservice.mappers;

import me.inquis1tor.userservice.dtos.AccountDetailsTransferDto;
import me.inquis1tor.userservice.entities.Account;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TransferAccountMapper {

    AccountDetailsTransferDto accountToTransfer(Account account);

    Account transferDtoToAccount(AccountDetailsTransferDto accountDetailsTransferDto);
}
