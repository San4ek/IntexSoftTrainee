package me.inquis1tor.userservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.inquis1tor.userservice.dtos.AccountResponseDto;
import me.inquis1tor.userservice.dtos.AccountTransferDto;
import me.inquis1tor.userservice.dtos.CredentialsTransferDto;
import me.inquis1tor.userservice.entities.AccountEntity;
import me.inquis1tor.userservice.entities.AccountStatus;
import me.inquis1tor.userservice.entities.PersonalInfoEntity;
import me.inquis1tor.userservice.mappers.AccountMapper;
import me.inquis1tor.userservice.repositories.AccountRepository;
import me.inquis1tor.userservice.services.AccountFinderService;
import me.inquis1tor.userservice.services.AccountService;
import me.inquis1tor.userservice.utils.LoggedAccountDetailsProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * An implementation of an {@link AccountService}.
 *
 * @author Alexander Sankevich
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final LoggedAccountDetailsProvider loggedAccountDetailsProvider;
    private final AccountFinderService accountFinderService;
    private final AccountMapper accountMapper;

    /**
     * Creates new {@link AccountEntity} with info from parameter {@code dto}.
     *
     * @param dto the new {@link AccountEntity} details
     */
    @Override
    @Transactional
    public void createAccount(final AccountTransferDto dto) {
        log.info("Creating account '{}'", dto.id());
        PersonalInfoEntity personalInfoEntity = new PersonalInfoEntity();
        personalInfoEntity.setId(dto.id());
        AccountEntity accountEntity = accountMapper.transferDtoToAccount(dto);
        accountEntity.setStatus(AccountStatus.ACTIVE);
        accountEntity.setPersonalInfo(personalInfoEntity);
        accountRepository.save(accountEntity);
        log.info("Account '{}' created", dto.id());
    }

    /**
     * Provides an {@link AccountEntity} email,
     * identified by the provided parameter {@code accountId}
     *
     * @param accountId an {@link AccountEntity} identifier
     * @return an {@link AccountEntity} email
     */
    @Override
    public String getEmail(final UUID accountId) {
        AccountEntity accountEntity = accountFinderService.findActiveAny(accountId);
        return accountEntity.getEmail();
    }

    /**
     * Provides current {@link LoggedAccountDetailsProvider logged} {@link AccountEntity} info.
     *
     * @return the {@link AccountResponseDto}
     */
    @Override
    @Transactional
    public AccountResponseDto getAccount() {
        return accountMapper.accountToDto(accountFinderService.findActiveAny(loggedAccountDetailsProvider.getAccountId()));
    }

    /**
     * Provides all {@link AccountEntity} info.
     *
     * @return the {@link List} of {@link AccountResponseDto}
     */
    @Override
    @Transactional
    public List<AccountResponseDto> getAll() {
        log.info("User '{}' requested all accounts info", loggedAccountDetailsProvider.getAccountId());
        return accountMapper.accountListToDtoList(accountRepository.findAll());
    }

    /**
     * Deletes an {@link AccountEntity} identified by
     * the provided parameter {@code accountId}.
     */
    @Override
    @Transactional
    public void deleteAccount(final UUID accountId) {
        log.info("Deleting account '{}'", accountId);
        AccountEntity accountEntity = accountFinderService.findActiveAny(accountId);
        accountEntity.setStatus(AccountStatus.DELETED);
        accountEntity.setDeletedDate(LocalDateTime.now());
        accountRepository.save(accountEntity);
        log.info("Account '{}' deleted", accountId);
    }

    /**
     * Blocks an {@link AccountEntity account} identified by
     * the provided parameter {@code accountId}.
     *
     * @param accountId the being blocked {@link AccountEntity} id
     * @param adminId   the admin id, who blocked {@link AccountEntity}
     */
    @Override
    @Transactional
    public void blockAccount(final UUID accountId, final UUID adminId) {
        log.info("Blocking account '{}' by '{}'", accountId, adminId);
        AccountEntity accountEntity = accountFinderService.findActiveNotAdmin(accountId);
        accountEntity.setStatus(AccountStatus.BLOCKED);
        accountEntity.setBlockedBy(adminId);
        accountEntity.setBlockedDate(LocalDateTime.now());
        accountRepository.save(accountEntity);
        log.info("Account '{}' blocked", accountId);
    }

    /**
     * Unblocks an {@link AccountEntity} identified by
     * the provided parameter {@code accountId}.
     *
     * @param accountId the being unblocked {@link AccountEntity} id
     */
    @Override
    @Transactional
    public void unblockAccount(final UUID accountId) {
        log.info("Unblocking account '{}'", accountId);
        AccountEntity accountEntity = accountFinderService.findBlockedNotAdmin(accountId);
        accountEntity.setStatus(AccountStatus.ACTIVE);
        accountEntity.setBlockedBy(null);
        accountEntity.setBlockedDate(null);
        accountRepository.save(accountEntity);
        log.info("Account '{}' unblocked", accountId);
    }

    /**
     * Updates an {@link AccountEntity} identified by the id
     * provided by the parameter {@code dto} with info provided
     * by the parameter {@code dto}.
     *
     * @param dto the {@link AccountEntity} new details.
     */
    @Override
    public void updateCredentials(final CredentialsTransferDto dto) {
        log.info("Updating '{}' account credentials", dto.id());
        AccountEntity accountEntity = accountFinderService.findActiveAny(dto.id());
        accountEntity.setEmail(dto.email());
        accountRepository.save(accountEntity);
        log.info("Account '{}' credentials updated", dto.id());
    }
}
