package com.example.transactionalMicroservice.repository;

import com.example.transactionalMicroservice.model.TransactionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface TransactionalRepository extends JpaRepository<TransactionalEntity, UUID> {
}
