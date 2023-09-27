package com.origami.service;

import com.origami.domain.Profile;
import com.origami.domain.User;
import com.origami.repository.ProfileRepository;
import com.origami.service.dto.DeathMailDTO;
import com.origami.service.dto.LifeStatusChangeDTO;
import com.origami.service.dto.RevivalMailDTO;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Locale;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import tech.jhipster.config.JHipsterProperties;

/**
 * Service for sending emails.
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";

    private static final String BASE_URL = "baseUrl";

    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    private final ProfileRepository profileRepository;

    public MailService(
        JHipsterProperties jHipsterProperties,
        JavaMailSender javaMailSender,
        MessageSource messageSource,
        SpringTemplateEngine templateEngine,
        ProfileRepository profileRepository
    ) {
        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
        this.profileRepository = profileRepository;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug(
            "Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart,
            isHtml,
            to,
            subject,
            content
        );

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        } catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}'", to, e);
        }
    }

    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
        if (user.getEmail() == null) {
            log.debug("Email doesn't exist for user '{}'", user.getLogin());
            return;
        }
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/activationEmail", "email.activation.title");
    }

    @Async
    public void sendCreationEmail(User user) {
        log.debug("Sending creation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/creationEmail", "email.activation.title");
    }

    @Async
    public void sendPasswordResetMail(User user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/passwordResetEmail", "email.reset.title");
    }

    @Async
    public void sendRevivalMail(RevivalMailDTO revivalMailDTO) {
        StringBuilder stringBuilder = new StringBuilder("Hello, are u still there?");
        stringBuilder
            .append("\n")
            .append(
                "If that is so, then copy link from below and paste it into your browser so you can notify your family that u aren't dead"
            )
            .append("\n")
            .append(jHipsterProperties.getMail().getBaseUrl() + "/iamalive/recover/" + revivalMailDTO.getLifeLink());
        sendEmail(revivalMailDTO.getUserEmail(), "Important account notification", stringBuilder.toString(), false, false);
    }

    @Async
    public void sendAfterDeadTemporaryPassword(DeathMailDTO deathMailDTO) {
        StringBuilder stringBuilder = new StringBuilder("We're sorry for your loss");
        stringBuilder
            .append("\n")
            .append("Your friend/relative account has been permanently blocked")
            .append("\n")
            .append("\n")
            .append("Here are your friends credentials and temporary password")
            .append("\n")
            .append("So you can login and gain access to fields that he/she hidden from public profile")
            .append("\n")
            .append("His/Her email" + deathMailDTO.getUserEmail())
            .append("\n")
            .append("Temp password" + deathMailDTO.getTempPassword());
        sendEmail(deathMailDTO.getFriendEmail(), "Important account notification", stringBuilder.toString(), false, false);
    }

    @Async
    public void sendWereGladYoureBack(String emailReceiver) {
        StringBuilder stringBuilder = new StringBuilder("We're glad that you're alive");
        stringBuilder.append("\n").append("Your accounts status has been updated from Unknown to Alive ").append("\n").append("\n");
        sendEmail(emailReceiver, "We're glad that you're alive", stringBuilder.toString(), false, false);
    }
}
