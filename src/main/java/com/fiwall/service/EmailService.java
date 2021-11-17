package com.fiwall.service;

import com.fiwall.dto.ReceiptTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmailService {


    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(ReceiptTransaction receiptTransaction) {
        try {
            var message = new SimpleMailMessage();
            message.setFrom(receiptTransaction.getEmailFrom());
            message.setTo(receiptTransaction.getEmailTo());
            message.setSubject(receiptTransaction.getSubject());
            message.setText(receiptTransaction.getDescription());
            emailSender.send(message);

        } catch (MailException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not Sent");
        }
    }
}
