package com.example.core.dto.event;

import java.math.BigDecimal;

public record TransactionalCreatedEvent(
        String transactionId,
        long from,
        long to,
        BigDecimal amount
) {
}
