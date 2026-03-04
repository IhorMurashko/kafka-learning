package com.example.transactionalMicroservice.dto;

import com.example.core.constants.TransactionStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO for {@link com.example.transactionalMicroservice.model.TransactionalEntity}
 */
public record TransactionalEntityDto(UUID id, Long senderId, Long receiverId, BigDecimal amount,
                                     TransactionStatus transactionStatus) implements Serializable {
}