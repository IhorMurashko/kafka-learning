package com.example.loggingMicroservice.service;

import com.example.core.dto.event.logging.LoggingDto;
import com.example.core.topics.TransactionalTopic;
import com.example.loggingMicroservice.model.LoggerExampleModel;
import com.example.loggingMicroservice.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(topics = TransactionalTopic.WRITE_LOG)
@Slf4j
public class LoggerServiceImpl implements LoggerService {
    private final LogRepository logRepository;

    @KafkaHandler
    @Override
    public void log(@Payload LoggingDto loggingDto) {
        log.info("Received logging event: {}", loggingDto);
        logRepository.save(new LoggerExampleModel(
                loggingDto.date(),
                loggingDto.message(),
                loggingDto.serviceSender()
        ));
        log.info("Logging event saved");
    }
}