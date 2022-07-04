package com.example.antifraud.repository;

import com.example.antifraud.model.StolenCardModel;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface StolenCardRepository extends CrudRepository<StolenCardModel, Long> {
    StolenCardModel findStolenCardModelByNumber(String number);

    Optional<StolenCardModel> findCardByNumber(String ip);

    List<StolenCardModel> findAll();
}
