package com.owerp.fmsprovider.system.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    private final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine tempEngine;

    public EmailService(JavaMailSender javaMailSender, SpringTemplateEngine tempEngine) {
        this.javaMailSender = javaMailSender;
        this.tempEngine = tempEngine;
    }

    public void sendEmail(String receiverAddress) {

        try {
            MimeMessage message = this.javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            Context context = new Context();

            String html = this.tempEngine.process("create-new-user", context);

            helper.setTo("asitha.de.alwis93@gmail.com");
            helper.setText(html, true);
            helper.setSubject("New User created");
            helper.setFrom("onework.erp@gmail.com");
            this.javaMailSender.send(message);
        } catch (MessagingException e) {
           this.logger.error(e.getMessage());
        }
    }

    public void sendBasicEmail(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("onework.erp@gmail.com");
        message.setTo("asitha.de.alwis93@gmail.com");
        message.setSubject("Test");
        message.setText("Test");
        this.javaMailSender.send(message);
    }

}
