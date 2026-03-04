package com.example.transactionalMicroservice.saga;

import com.example.core.dto.event.transaction.TransactionChangedStatusDto;
import com.example.core.headers.KafkaHeaderNames;
import com.example.transactionalMicroservice.dto.TransactionalEntityDto;
import com.example.transactionalMicroservice.service.TransactionalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.example.core.topics.TransactionalTopic.TRANSACTION_PROCESSED_EVENT_TOPIC;

@Component
@RequiredArgsConstructor
@Slf4j
@KafkaListener(topics = {TRANSACTION_PROCESSED_EVENT_TOPIC},
        groupId = "transactional-group")
public class SagaTransactionOrchestration {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final TransactionalService transactionalService;

    @KafkaHandler
    public void handle(@Payload TransactionChangedStatusDto transactionChangedStatusDto,
                       @Header(KafkaHeaderNames.MESSAGE_ID) String messageId,
                       @Header(KafkaHeaders.RECEIVED_KEY) String messageKey) {

        transactionalService.save(new TransactionalEntityDto(
                UUID.fromString(transactionChangedStatusDto.transactionId()),
                null,
                null,
                null,
                transactionChangedStatusDto.status()
        ));

//            todo: kafka logger
    }
}
