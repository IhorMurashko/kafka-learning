package com.example.loggingMicroservice.config;

import com.example.core.topics.TransactionalTopic;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class LoggingMicroserviceConfig {

    @Bean
    public NewTopic transactionalChangedStatusTopic() {
        return TopicBuilder.name(TransactionalTopic.WRITE_LOG)
                .partitions(3)
                .replicas(3)
                .configs(Map.of("min.insync.replicas", "2"))
                .build();
    }
}
