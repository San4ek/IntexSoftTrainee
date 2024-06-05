package me.inquis1tor.userservice.entities.account;

import me.inquis1tor.userservice.entities.credentials.AccountCredentials;
import me.inquis1tor.userservice.entities.personalinfo.Initials;
import me.inquis1tor.userservice.entities.role.Title;
import me.inquis1tor.userservice.entities.status.AccountStatus;

public interface AccountInfo {

    AccountCredentials getAccountCredentials();
    Initials getPersonalInfo();
    Title getRoleTitle();
    AccountStatus getAccountStatus();

}
