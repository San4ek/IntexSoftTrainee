package me.inquis1tor.userservice.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.inquis1tor.userservice.dtos.PersonalInfoDto;
import me.inquis1tor.userservice.entities.AccountEntity;
import me.inquis1tor.userservice.entities.PersonalInfoEntity;
import me.inquis1tor.userservice.mappers.PersonalInfoMapper;
import me.inquis1tor.userservice.repositories.PersonalInfoRepository;
import me.inquis1tor.userservice.services.PersonalInfoService;
import me.inquis1tor.userservice.utils.LoggedAccountDetailsProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * An implementation of an {@link PersonalInfoService}.
 *
 * @author Alexander Sankevich
 */
@Slf4j
@Service
@AllArgsConstructor
public class PersonalInfoServiceImpl implements PersonalInfoService {

    private final PersonalInfoRepository personalInfoRepository;
    private final LoggedAccountDetailsProvider loggedAccountDetailsProvider;
    private final PersonalInfoMapper personalInfoMapper;

    /**
     * Updates current {@link LoggedAccountDetailsProvider logged} {@link AccountEntity} with
     * personal info provided by the parameter {@code dto}.
     *
     * @param dto the {@link AccountEntity} new personal info
     */
    @Override
    @Transactional
    public void updatePersonalInfo(final PersonalInfoDto dto) {
        log.info("Updating '{}' personal info", loggedAccountDetailsProvider.getAccountId());
        PersonalInfoEntity personalInfoEntity = personalInfoMapper.dtoToPersonalInfo(dto);
        personalInfoEntity.setId(loggedAccountDetailsProvider.getAccountId());
        personalInfoRepository.save(personalInfoEntity);
        log.info("Personal info '{}' updated", loggedAccountDetailsProvider.getAccountId());
    }
}
