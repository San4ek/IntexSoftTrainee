package me.inqu1sitor.services.iml;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.inqu1sitor.dto.SendMailRequestDto;
import me.inqu1sitor.services.MailSendingService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * A {@code spring-mail} implementation of {@link MailSendingService}.
 *
 * @author Alexander Sankevich
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MailSendingServiceImpl implements MailSendingService {

    private final JavaMailSender emailSender;

    /**
     * Send email with the args, provided by the parameter {@code dto}.
     *
     * @param dto the {@link SendMailRequestDto}
     */
    @Override
    public void sendMail(final SendMailRequestDto dto) {
        log.info("Sending mail {}", dto);
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sankevich2003@mail.ru");
        message.setTo(dto.receiver());
        message.setSubject(dto.subject());
        message.setText(dto.body());
        emailSender.send(message);
    }
}
