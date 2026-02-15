package com.example.transactionalMicroservice.config;

import com.example.core.topice.KafkaTopicsName;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class TransactionalServiceConfig {

    @Bean
    public NewTopic transactionalCreatedEventTopic() {
        return TopicBuilder.name(KafkaTopicsName.TRANSACTION_CREATED_EVENT_TOPIC)
                .partitions(3)
                .replicas(3)
                .configs(Map.of("min.insync.replicas", "2"))
                .build();
    }
}