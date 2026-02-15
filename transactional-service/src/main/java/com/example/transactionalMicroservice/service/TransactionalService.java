package com.example.transactionalMicroservice.service;

import com.example.core.dto.web.TransactionalRequest;

import java.util.concurrent.ExecutionException;

public interface TransactionalService {
    void createTransaction(TransactionalRequest request) throws ExecutionException, InterruptedException;
}
