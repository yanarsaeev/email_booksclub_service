package com.booksclub.mail.repository;

import com.booksclub.mail.entity.MailVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MailVerificationRepository extends JpaRepository<MailVerificationToken, Long> {
    Optional<MailVerificationToken> findByToken(String token);
}
