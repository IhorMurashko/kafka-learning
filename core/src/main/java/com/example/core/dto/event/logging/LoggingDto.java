package com.example.core.dto.event.logging;

import java.time.Instant;

public record LoggingDto(
        Instant date,
        String message,
        String serviceSender
) {
}