package com.example.internesempla.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String toEmail, String subject, String message) {
        var email = new SimpleMailMessage();
        email.setTo(toEmail);
        email.setText(message);
        email.setSubject(subject);

        javaMailSender.send(email);
    }
}