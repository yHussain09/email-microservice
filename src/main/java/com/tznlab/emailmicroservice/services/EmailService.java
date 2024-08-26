package com.tznlab.emailmicroservice.services;

import com.tznlab.emailmicroservice.entities.EmailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface EmailService {
    EmailEntity sendEmail(EmailEntity emailEntity);

    Page<EmailEntity> findAll(Pageable pageable);

    Optional<EmailEntity> findById(UUID emailId);
}
