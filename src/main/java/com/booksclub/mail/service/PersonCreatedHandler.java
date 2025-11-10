package com.booksclub.mail.service;

import com.booksclub.mail.entity.PersonCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@KafkaListener(topics = "person-created-topic")
public class PersonCreatedHandler {

    private final MailVerificationService mailVerificationService;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @KafkaHandler
    public void handle(PersonCreatedEvent personCreatedEvent) {
        LOGGER.info("Person: {}", personCreatedEvent.getEmail());
        mailVerificationService.generateToken(personCreatedEvent.getEmail());
    }
}
