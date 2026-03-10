package com.example.loggingMicroservice.repository;

import com.example.loggingMicroservice.model.LoggerExampleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<LoggerExampleModel, Long> {
}
