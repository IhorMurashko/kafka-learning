package com.example.transactionalMicroservice.service;

import com.example.core.dto.web.TransactionalRequest;
import com.example.transactionalMicroservice.dto.TransactionalEntityDto;

import java.util.concurrent.ExecutionException;

public interface TransactionalService {
    void createTransaction(TransactionalRequest request) throws ExecutionException, InterruptedException;

    void save(TransactionalEntityDto transactionalEntityDto);
}
