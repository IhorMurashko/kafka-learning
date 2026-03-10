package com.example.core.dto.event.transaction;

import java.math.BigDecimal;

public record TransactionalCreatedEvent(
        String transactionId,
        long senderId,
        long receiverId,
        BigDecimal amount
) {
}