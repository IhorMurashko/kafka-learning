package com.example.transactionalMicroservice.service;

import com.example.core.dto.event.TransactionalCreatedEvent;
import com.example.core.dto.web.TransactionalRequest;
import com.example.core.topics.TransactionalTopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionalServiceImpl implements TransactionalService {
    private final KafkaTemplate<String, TransactionalCreatedEvent> kafkaTemplate;

    @Override
    public void createTransaction(TransactionalRequest request) throws ExecutionException, InterruptedException {
        UUID transactionId = UUID.randomUUID();
        log.info("Transaction created with id: {}", transactionId);
        TransactionalCreatedEvent event = new TransactionalCreatedEvent(
                transactionId.toString(), request.from(), request.to(), request.amount());
        SendResult<String, TransactionalCreatedEvent> sendResult = kafkaTemplate.send(TransactionalTopic.TRANSACTION_CREATED_EVENT_TOPIC,
                transactionId.toString(),
                event).get();
        log.info("Transaction created event sent to kafka with key {}", transactionId);
        log.info("Topic: {}, partition: {}, Offset: {}",
                sendResult.getRecordMetadata().topic(),
                sendResult.getRecordMetadata().partition(),
                sendResult.getRecordMetadata().offset());
    }
}