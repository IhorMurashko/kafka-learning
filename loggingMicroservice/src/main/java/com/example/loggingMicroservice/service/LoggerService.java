package com.example.loggingMicroservice.service;

import com.example.core.dto.event.logging.LoggingDto;

public interface LoggerService {
    void log(LoggingDto loggingDto);
}
