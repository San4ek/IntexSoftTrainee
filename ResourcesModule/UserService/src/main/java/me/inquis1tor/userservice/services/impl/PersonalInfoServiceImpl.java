package me.inquis1tor.userservice.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.inquis1tor.userservice.dtos.PersonalInfoDto;
import me.inquis1tor.userservice.entities.AccountEntity;
import me.inquis1tor.userservice.entities.PersonalInfoEntity;
import me.inquis1tor.userservice.mappers.PersonalInfoMapper;
import me.inquis1tor.userservice.repositories.PersonalInfoRepository;
import me.inquis1tor.userservice.services.PersonalInfoService;
import me.inquis1tor.userservice.utils.LoggedAccountDetailsHolder;
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
    private final LoggedAccountDetailsHolder loggedAccountDetailsHolder;
    private final PersonalInfoMapper personalInfoMapper;

    /**
     * Updates current {@link LoggedAccountDetailsHolder logged} {@link AccountEntity} with
     * personal info provided by the parameter {@code dto}.
     *
     * @param dto the {@link AccountEntity} new personal info
     */
    @Override
    @Transactional
    public void updatePersonalInfo(final PersonalInfoDto dto) {
        log.info("Updating '{}' personal info", loggedAccountDetailsHolder.getAccountId());
        PersonalInfoEntity personalInfoEntity = personalInfoMapper.dtoToPersonalInfo(dto);
        personalInfoEntity.setId(loggedAccountDetailsHolder.getAccountId());
        personalInfoRepository.save(personalInfoEntity);
        log.info("Personal info '{}' updated", loggedAccountDetailsHolder.getAccountId());
    }
}
