package com.example.antifraud.repository;

import com.example.antifraud.model.TransactionModel;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface TransactionRepository extends CrudRepository<TransactionModel,Long> {
}
