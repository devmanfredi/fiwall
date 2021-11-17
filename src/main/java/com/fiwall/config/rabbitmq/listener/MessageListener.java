package com.fiwall.config.rabbitmq.listener;

import com.fiwall.config.rabbitmq.RabbitMQConfig;
import com.fiwall.dto.ReceiptTransaction;
import com.fiwall.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageListener {

    @Autowired
    EmailService emailService;

    @RabbitListener(queues = RabbitMQConfig.TRANSACTION_QUEUE)
    public void listener(@Payload ReceiptTransaction receiptTransaction) {
        log.info("receiptTransaction => " + receiptTransaction.toString());
        emailService.sendEmail(receiptTransaction);

    }
}
