package com.tznlab.emailmicroservice.consumers;

import com.tznlab.emailmicroservice.dtos.EmailDto;
import com.tznlab.emailmicroservice.entities.EmailEntity;
import com.tznlab.emailmicroservice.services.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private final EmailService emailService;

    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.email.queue}")
    public void listen(@Payload EmailDto emailDto) {
        EmailEntity emailEntity = new EmailEntity();
        BeanUtils.copyProperties(emailDto, emailEntity);
        emailService.sendEmail(emailEntity);
        System.out.println("Email Status: " + emailEntity.getStatusEmail().toString());
    }
}
