package com.booksclub.mail.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {

    private final JavaMailSender javaMailSender;

    public MailSenderService(JavaMailSender mailSender) {
        this.javaMailSender = mailSender;
    }

    public void sendVerificationMail(String to, String link) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ggntubooksclub@bk.ru");
        message.setTo(to);
        message.setSubject("Mail Confirmation");
        message.setText("Please confirm your email by clicking on the link: " + link);
        javaMailSender.send(message);
    }
}
