package me.inqu1sitor.authservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.inqu1sitor.authservice.clients.UserServiceClient;
import me.inqu1sitor.authservice.dtos.CredentialsRequestDto;
import me.inqu1sitor.authservice.dtos.SendMailRequestDto;
import me.inqu1sitor.authservice.entities.AccountEntity;
import me.inqu1sitor.authservice.entities.AccountRole;
import me.inqu1sitor.authservice.entities.AccountStatus;
import me.inqu1sitor.authservice.mappers.AccountMapper;
import me.inqu1sitor.authservice.rabbit.Notifier;
import me.inqu1sitor.authservice.rabbit.impl.RabbitAccountDeletedNotifierImpl;
import me.inqu1sitor.authservice.repositories.AccountRepository;
import me.inqu1sitor.authservice.services.AccountFinderService;
import me.inqu1sitor.authservice.services.AccountService;
import me.inqu1sitor.authservice.services.LogoutService;
import me.inqu1sitor.authservice.utils.LoggedAccountDetailsProvider;
import me.inqu1sitor.authservice.utils.SendMailRequestDtoProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

/**
 * An implementation of an {@link AccountService}.
 *
 * @author Alexander Sankevich
 */
@Slf4j
@Validated
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoggedAccountDetailsProvider loggedAccountDetailsProvider;
    private final Notifier<UUID> accountDeletedNotifier;
    private final Notifier<SendMailRequestDto> sendMailNotifier;
    private final AccountFinderService accountFinderService;
    private final UserServiceClient userServiceClient;
    private final AccountMapper accountMapper;
    private final LogoutService logoutService;

    /**
     * Creates new {@link AccountEntity} with credentials from parameter {@code credentialsRequestDto}
     * and role from {@code role} and {@link UserServiceClient notify} about it user service.
     *
     * @param credentialsRequestDto the new {@link AccountEntity} credentials
     * @param role                  the new {@link AccountEntity} {@link AccountRole role}
     */
    @Override
    @Transactional
    public void createAccount(final CredentialsRequestDto credentialsRequestDto, final AccountRole role) {
        log.info("Creating {} account", role);
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setPassword(passwordEncoder.encode(credentialsRequestDto.password()));
        accountEntity.setEmail(credentialsRequestDto.email());
        accountEntity.setRole(role);
        accountEntity.setStatus(AccountStatus.ACTIVE);
        accountRepository.save(accountEntity);
        userServiceClient.register(accountMapper.toAccountTransferDto(accountEntity));
        sendMailNotifier.notifyAbout(SendMailRequestDtoProvider.registerDto(accountEntity.getId()));
        log.info("Account '{}' created", accountEntity.getId());
    }

    /**
     * Updates current {@link LoggedAccountDetailsProvider logged} {@link AccountEntity} with
     * credentials provided by parameter <code>credentialsRequestDto</code>,
     * {@link LogoutServiceImpl#logoutAll() logout} and {@link UserServiceClient notify}
     * about it user service.
     *
     * @param credentialsRequestDto the {@link LoggedAccountDetailsProvider logged} {@link AccountEntity}
     *                              new credentials
     */
    @Override
    @Transactional
    public void updateAccount(final CredentialsRequestDto credentialsRequestDto) {
        log.info("Updating account '{}'", loggedAccountDetailsProvider.getAccountId());
        AccountEntity accountEntity = accountFinderService.findActiveAny(loggedAccountDetailsProvider.getAccountId());
        accountEntity.setPassword(passwordEncoder.encode(credentialsRequestDto.password()));
        accountEntity.setEmail(credentialsRequestDto.email());
        accountRepository.save(accountEntity);
        logoutService.logoutAll();
        userServiceClient.update(accountMapper.toCredentialsTransferDto(accountEntity));
        sendMailNotifier.notifyAbout(SendMailRequestDtoProvider.updateDto(loggedAccountDetailsProvider.getAccountId()));
        log.info("Account '{}' updated  and logged out", loggedAccountDetailsProvider.getAccountId());
    }

    /**
     * Blocks {@link AccountEntity account} identified by the provided parameter {@code accountId},
     * {@link LogoutServiceImpl#logoutAll logout} and {@link UserServiceClient notify} about it
     * user service.
     *
     * @param accountId the being blocked {@link AccountEntity} id
     */
    @Override
    @Transactional
    public void blockAccount(final UUID accountId) {
        log.info("Blocking account '{}'", loggedAccountDetailsProvider.getAccountId());
        AccountEntity accountEntity = accountFinderService.findActiveNotAdmin(accountId);
        accountEntity.setId(accountId);
        accountEntity.setStatus(AccountStatus.BLOCKED);
        accountRepository.save(accountEntity);
        logoutService.logoutAll(accountId);
        userServiceClient.block(accountId, loggedAccountDetailsProvider.getAccountId());
        sendMailNotifier.notifyAbout(SendMailRequestDtoProvider.blockDto(accountId));
        log.info("Account '{}' blocked  and logged out", loggedAccountDetailsProvider.getAccountId());
    }

    /**
     * Unblocks {@link AccountEntity} identified by the provided parameter {@code accountId}
     * and {@link UserServiceClient notify} about it user service.
     *
     * @param accountId the being unblocked {@link AccountEntity} id
     */
    @Override
    @Transactional
    public void unblockAccount(final UUID accountId) {
        log.info("Unblocking account '{}'", loggedAccountDetailsProvider.getAccountId());
        AccountEntity accountEntity = accountFinderService.findBlockedNotAdmin(accountId);
        accountEntity.setId(accountId);
        accountEntity.setStatus(AccountStatus.ACTIVE);
        accountRepository.save(accountEntity);
        userServiceClient.unblock(accountId);
        sendMailNotifier.notifyAbout(SendMailRequestDtoProvider.unblockDto(accountId));
        log.info("Account '{}' unblocked", loggedAccountDetailsProvider.getAccountId());
    }

    /**
     * Deletes current {@link LoggedAccountDetailsProvider logged} {@link AccountEntity},
     * {@link LogoutServiceImpl#logoutAll() logout} and {@link RabbitAccountDeletedNotifierImpl notify}
     * about it all services.
     */
    @Override
    @Transactional
    public void deleteAccount() {
        log.info("Deleting account '{}'", loggedAccountDetailsProvider.getAccountId());
        AccountEntity accountEntity = accountFinderService.findActiveAny(loggedAccountDetailsProvider.getAccountId());
        accountEntity.setStatus(AccountStatus.DELETED);
        accountRepository.save(accountEntity);
        logoutService.logoutAll();
        accountDeletedNotifier.notifyAbout(loggedAccountDetailsProvider.getAccountId());
        sendMailNotifier.notifyAbout(SendMailRequestDtoProvider.deleteDto(loggedAccountDetailsProvider.getAccountId()));
        log.info("Account '{}' deleted and logged out", loggedAccountDetailsProvider.getAccountId());
    }
}
