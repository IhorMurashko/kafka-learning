package com.example.core.dto.event;

import java.math.BigDecimal;

public record TransactionalCreatedEvent(
        String transactionId,
        long senderId,
        long receiverId,
        BigDecimal amount
) {
}