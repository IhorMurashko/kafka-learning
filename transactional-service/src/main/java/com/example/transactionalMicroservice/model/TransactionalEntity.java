package com.example.transactionalMicroservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TransactionalEntity {
    @Id
    private UUID id;
    private long senderId;
    private long receiverId;
    private BigDecimal amount;
}
