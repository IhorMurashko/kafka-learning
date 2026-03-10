package com.example.loggingMicroservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoggerExampleModel {
    @Id
    @GeneratedValue
    private long id;
    private Instant date;
    private String message;
    private String serviceSender;

    public LoggerExampleModel(Instant date, String message, String serviceSender) {
        this.date = date;
        this.message = message;
        this.serviceSender = serviceSender;
    }
}