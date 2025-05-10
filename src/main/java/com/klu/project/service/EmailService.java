package com.klu.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {
    
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Sends an HTML email asynchronously.
     *
     * @param to      recipient email address
     * @param subject subject of the email
     * @param body    HTML content of the email
     * @throws MessagingException if sending fails
     */
    @Async
    public void sendHtmlEmail(String to, String subject, String body) throws MessagingException {
        try {
            logger.info("Preparing to send email to: {}", to);
            
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);  // true = HTML

            logger.info("Email content prepared, attempting to send...");
            mailSender.send(message);
            logger.info("Email sent successfully to: {}", to);
        } catch (MessagingException e) {
            logger.error("Failed to send email to: {}", to, e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error when sending email to: {}", to, e);
            throw new MessagingException("Failed to send email due to unexpected error", e);
        }
    }
}
