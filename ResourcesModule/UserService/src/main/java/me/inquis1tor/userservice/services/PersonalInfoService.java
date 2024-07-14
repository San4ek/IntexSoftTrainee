package me.inquis1tor.userservice.services;

import me.inquis1tor.userservice.dtos.PersonalInfoDto;
import me.inquis1tor.userservice.entities.PersonalInfoEntity;

/**
 * Implementations of this interface are responsible for
 * the management of {@link PersonalInfoEntity}.
 *
 * @author Alexander Sankevich
 */
public interface PersonalInfoService {

    void updatePersonalInfo(PersonalInfoDto dto);
}
