package com.example.core.dto.web;

import java.math.BigDecimal;

public record TransactionalRequest(
        long fromUserId,
        long toUserId,
        BigDecimal amount
) {
}