package com.example.core.dto.event.transaction;

import com.example.core.constants.TransactionStatus;

public record TransactionChangedStatusDto(
        String transactionId,
        TransactionStatus status,
        String message
) {
}