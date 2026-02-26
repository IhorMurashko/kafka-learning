package com.example.transactionalMicroservice.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;

import java.util.Map;
import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class TransactionalKafkaConfig {
    private final Environment environment;

    public Map<String, Object> producerConfigs() {
        return Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                Objects.requireNonNull(environment.getProperty("spring.kafka.bootstrap-servers")),
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                Objects.requireNonNull(environment.getProperty("spring.kafka.producer.key-serializer")),
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                Objects.requireNonNull(environment.getProperty("spring.kafka.producer.value-serializer")),
                ProducerConfig.ACKS_CONFIG,
                Objects.requireNonNull(environment.getProperty("spring.kafka.producer.acks")),
                ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG,
                Objects.requireNonNull(environment.getProperty("spring.kafka.producer.properties.delivery.timeout.ms")),
                ProducerConfig.LINGER_MS_CONFIG,
                Objects.requireNonNull(environment.getProperty("spring.kafka.producer.properties.linger.ms")),
                ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG,
                Objects.requireNonNull(environment.getProperty("spring.kafka.producer.properties.request.timeout.ms")),
                ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,
                Objects.requireNonNull(environment.getProperty("spring.kafka.producer.properties.enable.idempotence")),
                ProducerConfig.TRANSACTIONAL_ID_CONFIG,
                Objects.requireNonNull(environment.getProperty("spring.kafka.producer.transaction-id-prefix"))
        );
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaTransactionManager<String, Object> kafkaTransactionManager(ProducerFactory<String, Object> producerFactory) {
        return new KafkaTransactionManager<>(producerFactory);
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}