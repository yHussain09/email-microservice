package com.tznlab.emailmicroservice.services.impl;

import com.tznlab.emailmicroservice.constants.EmailStatus;
import com.tznlab.emailmicroservice.entities.EmailEntity;
import com.tznlab.emailmicroservice.repositories.EmailRepository;
import com.tznlab.emailmicroservice.services.EmailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;
    private final JavaMailSender emailSender;

    public EmailServiceImpl(EmailRepository emailRepository, JavaMailSender emailSender) {
        this.emailRepository = emailRepository;
        this.emailSender = emailSender;
    }

    @Override
    public EmailEntity sendEmail(EmailEntity emailEntity) {
        emailEntity.setSendDateEmail(LocalDateTime.now());
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailEntity.getEmailFrom());
            message.setTo(emailEntity.getEmailTo());
            message.setSubject(emailEntity.getSubject());
            message.setText(emailEntity.getText());
            emailSender.send(message);

            emailEntity.setStatusEmail(EmailStatus.SENT);
        } catch (MailException e) {
            emailEntity.setStatusEmail(EmailStatus.ERROR);
        }

        return emailRepository.save(emailEntity);
    }

    public Page<EmailEntity> findAll(Pageable pageable) {
        return emailRepository.findAll(pageable);
    }

    public Optional<EmailEntity> findById(UUID emailId) {
        return emailRepository.findById(emailId);
    }
}
