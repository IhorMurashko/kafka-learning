package com.example.transactionalMicroservice.saga;

import com.example.core.dto.event.transaction.TransactionChangedStatusDto;
import com.example.core.headers.KafkaHeaderNames;
import com.example.core.topics.TransactionalTopic;
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


@Component
@RequiredArgsConstructor
@Slf4j
@KafkaListener(topics = {TransactionalTopic.TRANSACTION_PROCESSED_EVENT_TOPIC},
groupId = "transaction-orchestration-group")
public class SagaTransactionOrchestration {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final TransactionalService transactionalService;

    @KafkaHandler
    public void handle(@Payload TransactionChangedStatusDto transactionChangedStatusDto,
                       @Header(value = KafkaHeaderNames.MESSAGE_ID, required = false) String messageId,
                       @Header(KafkaHeaders.RECEIVED_KEY) String messageKey) {
        log.info("Received event: {}", transactionChangedStatusDto);
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
