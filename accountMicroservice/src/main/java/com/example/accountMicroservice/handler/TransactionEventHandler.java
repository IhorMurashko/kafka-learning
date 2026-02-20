package com.example.accountMicroservice.handler;

import com.example.accountMicroservice.exception.NonRetryableException;
import com.example.accountMicroservice.exception.RetryableException;
import com.example.accountMicroservice.model.ProcessedEventEntity;
import com.example.accountMicroservice.repository.ProcessedEventRepository;
import com.example.core.dto.event.TransactionalCreatedEvent;
import com.example.core.headers.KafkaHeaderNames;
import com.example.core.topics.TransactionalTopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
@KafkaListener(topics = TransactionalTopic.TRANSACTION_CREATED_EVENT_TOPIC)
public class TransactionEventHandler {
    private final ProcessedEventRepository processedEventRepository;

    @Transactional
    @KafkaHandler
    public void handle(@Payload TransactionalCreatedEvent transactionalCreatedEvent,
                       @Header(KafkaHeaderNames.MESSAGE_ID) String messageId,
                       @Header(KafkaHeaders.RECEIVED_KEY) String messageKey) {
        log.info("Received event: {}", transactionalCreatedEvent);

        boolean existedByMessageId = processedEventRepository.existsByMessageId(messageId);
        if (existedByMessageId) {
            log.info("Duplicated message ID: {}", messageId);
            return;
        }


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
            throw new NonRetryableException("Error handling event: " + transactionalCreatedEvent);
        }

        try {
            processedEventRepository.save(
                    new ProcessedEventEntity(messageId, transactionalCreatedEvent.transactionId()));
            log.info("Processed event saved: {}", transactionalCreatedEvent);
        } catch (DataIntegrityViolationException ex) {
            log.error("Error saving processed event: {}", ex.getMessage());
            throw new NonRetryableException(ex.getMessage(), ex);
        }
    }
}