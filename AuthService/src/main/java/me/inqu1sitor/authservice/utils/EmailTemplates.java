package me.inqu1sitor.authservice.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailTemplates {

    public static final String ACCOUNT_REGISTERED_THEME = "Successful registration!";
    public static final String ACCOUNT_REGISTERED_MESSAGE = "Your account was registered successfully. If it was not yours decision, please, call our technical support.";
    public static final String ACCOUNT_DELETED_THEME = "Successful deleting!";
    public static final String ACCOUNT_DELETED_MESSAGE = "Your account was deleted successfully. If it was not yours decision, please, call our technical support.";
    public static final String ACCOUNT_BLOCKED_THEME = "Account was blocked!";
    public static final String ACCOUNT_BLOCKED_MESSAGE = "Your account was blocked. For more information, please, call our technical support.";
    public static final String ACCOUNT_UNBLOCKED_THEME = "Account was unblocked!";
    public static final String ACCOUNT_UNBLOCKED_MESSAGE = "Your account was unblocked. For more information, please, call our technical support.";
    public static final String CREDENTIALS_UPDATED_THEME = "Successful update!";
    public static final String CREDENTIALS_UPDATED_MESSAGE = "Your account credentials were updated successfully. If it was not yours decision, please, call our technical support.";
}
