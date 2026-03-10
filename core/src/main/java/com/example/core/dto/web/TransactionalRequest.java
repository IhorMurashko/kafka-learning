package com.example.core.dto.web;

import java.math.BigDecimal;

public record TransactionalRequest(
        long senderId,
        long receiverId,
        BigDecimal amount
) {
}