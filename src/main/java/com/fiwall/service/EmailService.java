package com.fiwall.service;

import com.fiwall.dto.ReceiptTransactionDto;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmailService {

    private final JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendEmail(ReceiptTransactionDto receiptTransactionDto) {
        try {
            var message = new SimpleMailMessage();
            message.setFrom(receiptTransactionDto.getEmailFrom());
            message.setTo(receiptTransactionDto.getEmailTo());
            message.setSubject(receiptTransactionDto.getSubject());
            message.setText(receiptTransactionDto.getDescription());
            emailSender.send(message);

        } catch (MailException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not Sent");
        }
    }
}
