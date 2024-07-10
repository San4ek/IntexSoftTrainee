package me.inquis1tor.userservice.services;

import me.inquis1tor.userservice.entities.PersonalInfo;

public interface PersonalInfoService {

    void updatePersonalInfo(PersonalInfo personalInfo);

    PersonalInfo getPersonalInfo();
}
