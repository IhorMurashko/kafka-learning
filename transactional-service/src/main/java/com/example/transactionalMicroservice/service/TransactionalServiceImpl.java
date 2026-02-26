package com.example.transactionalMicroservice.service;

import com.example.core.dto.event.TransactionalCreatedEvent;
import com.example.core.dto.web.TransactionalRequest;
import com.example.core.headers.KafkaHeaderNames;
import com.example.core.topics.TransactionalTopic;
import com.example.transactionalMicroservice.model.TransactionalEntity;
import com.example.transactionalMicroservice.repository.TransactionalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionalServiceImpl implements TransactionalService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final TransactionalRepository transactionalRepository;

    @Transactional("transactionManager")
    @Override
    public void createTransaction(TransactionalRequest request) throws ExecutionException, InterruptedException {
        log.info("Got transaction request: {}", request);

        UUID transactionId = UUID.randomUUID();
        log.info("Transaction id: {}", transactionId);

        TransactionalCreatedEvent event = new TransactionalCreatedEvent(
                transactionId.toString(), request.senderId(), request.receiverId(), request.amount());
        log.info("Transaction was created: {}", event);

        ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(
                TransactionalTopic.TRANSACTION_CREATED_EVENT_TOPIC,
                transactionId.toString(),
                event);
        producerRecord.headers()
                .add(KafkaHeaderNames.MESSAGE_ID, UUID.randomUUID().toString().getBytes());

        SendResult<String, Object> sendResult = kafkaTemplate
                .send(producerRecord).get();
        log.info("Event sent with key {}", transactionId);

        log.info("Topic: {}, partition: {}, Offset: {}",
                sendResult.getRecordMetadata().topic(),
                sendResult.getRecordMetadata().partition(),
                sendResult.getRecordMetadata().offset());

        transactionalRepository.save(new TransactionalEntity(
                transactionId,
                request.senderId(),
                request.receiverId(),
                request.amount()
        ));
        log.info("Transaction saved: {}", transactionId);
    }
}