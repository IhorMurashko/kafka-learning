package com.example.accountMicroservice.handler;

import com.example.accountMicroservice.exception.NonRetryableException;
import com.example.accountMicroservice.exception.RetryableException;
import com.example.core.dto.event.TransactionalCreatedEvent;
import com.example.core.topics.TransactionalTopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
@KafkaListener(topics = TransactionalTopic.TRANSACTION_CREATED_EVENT_TOPIC)
public class TransactionEventHandler {

    @KafkaHandler
    public void handle(TransactionalCreatedEvent transactionalCreatedEvent) {
        log.info("Received event: {}", transactionalCreatedEvent);
        Random random = new Random();
        int nextInt = random.nextInt(3);
        log.info("Random number: {}", nextInt);
        if (nextInt == 0) {
            log.info("Handled event: {}", transactionalCreatedEvent);
        } else if (nextInt == 1) {
            log.warn("Got RetryableException");
            throw new RetryableException("Error handling event: " + transactionalCreatedEvent);
        } else {
            log.warn("Got NonRetryableException");
            throw new NonRetryableException("Error handling event: " + transactionalCreatedEvent + "");
        }
    }
}