package com.example.accountMicroservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ProcessedEventEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String messageId;
    @Column(nullable = false)
    private String transactionId;

    public ProcessedEventEntity(String messageId, String transactionId) {
        this.messageId = messageId;
        this.transactionId = transactionId;
    }
}
