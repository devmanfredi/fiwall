package com.fiwall.config.rabbitmq.listener;

import com.fiwall.config.rabbitmq.RabbitMQConfig;
import com.fiwall.dto.ReceiptTransactionDto;
import com.fiwall.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageListener {

    EmailService emailService;

    public MessageListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = RabbitMQConfig.TRANSACTION_QUEUE)
    public void listener(@Payload ReceiptTransactionDto receiptTransactionDto) {
        log.info("receiptTransaction => " + receiptTransactionDto.toString());
        emailService.sendEmail(receiptTransactionDto);

    }
}
