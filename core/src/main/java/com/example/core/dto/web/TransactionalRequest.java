package com.example.core.dto.web;

import java.math.BigDecimal;

public record TransactionalRequest(
        long from,
        long to,
        BigDecimal amount
) {
}