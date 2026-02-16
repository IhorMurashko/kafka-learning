package com.example.accountMicroservice.handler;

import com.example.core.dto.event.TransactionalCreatedEvent;
import com.example.core.topics.TransactionalTopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@KafkaListener(topics = TransactionalTopic.TRANSACTION_CREATED_EVENT_TOPIC)
public class TransactionEventHandler {

    @KafkaHandler
    public void handle(TransactionalCreatedEvent transactionalCreatedEvent) {
        log.info("Received event: {}", transactionalCreatedEvent);
    }


}