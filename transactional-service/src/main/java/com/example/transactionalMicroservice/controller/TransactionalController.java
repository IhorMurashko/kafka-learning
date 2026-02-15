package com.example.transactionalMicroservice.controller;

import com.example.core.dto.web.TransactionalRequest;
import com.example.core.error.ErrorResponse;
import com.example.transactionalMicroservice.service.TransactionalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/transactional")
@RequiredArgsConstructor
@Slf4j
public class TransactionalController {
    private final TransactionalService transactionalService;

    @RequestMapping("/create")
    public ResponseEntity<Object> createTransaction(@RequestBody TransactionalRequest request) {
        try {
            transactionalService.createTransaction(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Transaction created successfully");
        } catch (Exception e) {
            log.error("Error creating transaction", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(
                            Instant.now(),
                            e.getMessage(),
                            "/api/v1/transactional/create"));
        }
    }
}
