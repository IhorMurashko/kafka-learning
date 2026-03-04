package com.example.core.dto.event.logging;

import java.time.Instant;
import java.util.UUID;

public record LoggingDto(
        UUID transactionId,
        Instant date,
        String message
) {
}
