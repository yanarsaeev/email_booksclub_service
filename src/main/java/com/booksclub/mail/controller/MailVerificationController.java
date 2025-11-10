package com.booksclub.mail.controller;

import com.booksclub.mail.service.MailSenderService;
import com.booksclub.mail.service.MailVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RestController
public class MailVerificationController {
    private final MailVerificationService mailVerificationService;
    private final MailSenderService mailSenderService;

    @Autowired
    public MailVerificationController(MailVerificationService mailVerificationService,
                                      MailSenderService mailSenderService) {
        this.mailVerificationService = mailVerificationService;
        this.mailSenderService = mailSenderService;
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generateToken(@RequestParam String email) {
        String token = mailVerificationService.generateToken(email);
        String confirmLink = "https://localhost:8081/api/auth/confirm?token=" + token;

        mailSenderService.sendVerificationMail(email, confirmLink);
        return ResponseEntity.ok("Token is generated");
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirmEmail(@RequestParam String token) {
        boolean success = mailVerificationService.verifyToken(token);
        if (success) {
            return ResponseEntity.ok("Email is confirmed");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid link");
        }
    }
}
