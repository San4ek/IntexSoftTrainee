package me.inqu1sitor.authservice.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.inqu1sitor.authservice.dtos.SendMailRequestDto;

import java.util.UUID;

/**
 * Provides a {@link SendMailRequestDto} with different templates.
 *
 * @author Alexander Sankevich
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SendMailRequestDtoProvider {

    public static SendMailRequestDto registerDto(final UUID accountId) {
        return new SendMailRequestDto(accountId, EmailTemplates.ACCOUNT_REGISTERED_THEME, EmailTemplates.ACCOUNT_REGISTERED_MESSAGE);
    }

    public static SendMailRequestDto updateDto(final UUID accountId) {
        return new SendMailRequestDto(accountId, EmailTemplates.CREDENTIALS_UPDATED_THEME, EmailTemplates.CREDENTIALS_UPDATED_MESSAGE);
    }

    public static SendMailRequestDto deleteDto(final UUID accountId) {
        return new SendMailRequestDto(accountId, EmailTemplates.ACCOUNT_DELETED_THEME, EmailTemplates.ACCOUNT_DELETED_MESSAGE);
    }

    public static SendMailRequestDto blockDto(final UUID accountId) {
        return new SendMailRequestDto(accountId, EmailTemplates.ACCOUNT_BLOCKED_THEME, EmailTemplates.ACCOUNT_BLOCKED_MESSAGE);
    }

    public static SendMailRequestDto unblockDto(final UUID accountId) {
        return new SendMailRequestDto(accountId, EmailTemplates.ACCOUNT_UNBLOCKED_THEME, EmailTemplates.ACCOUNT_UNBLOCKED_MESSAGE);
    }
}
