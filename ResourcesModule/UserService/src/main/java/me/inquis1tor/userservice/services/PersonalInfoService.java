package me.inquis1tor.userservice.services;

import me.inquis1tor.userservice.annotations.validation.uuid.AnyActiveUuid;
import me.inquis1tor.userservice.entities.PersonalInfo;

import java.util.UUID;

public interface PersonalInfoService {

    PersonalInfo updatePersonalInfo(@AnyActiveUuid UUID accountId, PersonalInfo personalInfo);

    PersonalInfo getPersonalInfo(@AnyActiveUuid UUID accountId);
}
