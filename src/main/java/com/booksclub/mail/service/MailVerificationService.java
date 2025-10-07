package com.booksclub.mail.service;

import com.booksclub.mail.entity.MailVerificationToken;
import com.booksclub.mail.repository.MailVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class MailVerificationService {

    private final MailVerificationRepository mailVerificationRepository;

    @Autowired
    public MailVerificationService(MailVerificationRepository repository) {
        this.mailVerificationRepository = repository;
    }

    public String generateToken(String mail) {
        String token = UUID.randomUUID().toString();

        MailVerificationToken mailVerificationToken = new MailVerificationToken();
        mailVerificationToken.setToken(token);
        mailVerificationToken.setMail(mail);
        mailVerificationToken.setExpiryDate(LocalDateTime.now().plusMonths(3));
        mailVerificationToken.setUsed(false);

        mailVerificationRepository.save(mailVerificationToken);
        return token;
    }

    public boolean verifyToken(String token) {
        Optional<MailVerificationToken> optionalToken = mailVerificationRepository.findByToken(token);
        if (optionalToken.isEmpty()) {
            return false;
        }

        MailVerificationToken verificationToken = optionalToken.get();
        if (verificationToken.isUsed()) {
            return false;
        }
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return false;
        }

        verificationToken.setUsed(true);
        mailVerificationRepository.save(verificationToken);
        return true;
    }
}
