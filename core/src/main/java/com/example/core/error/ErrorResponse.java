package com.example.core.error;

import java.time.Instant;

public record ErrorResponse(
        Instant date,
        String message,
        String path
) {
}
