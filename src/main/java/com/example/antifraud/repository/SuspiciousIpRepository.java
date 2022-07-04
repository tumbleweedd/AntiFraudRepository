package com.example.antifraud.repository;

import com.example.antifraud.model.SuspiciousIpModel;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface SuspiciousIpRepository extends CrudRepository<SuspiciousIpModel, Long> {

    SuspiciousIpModel findSuspiciousIpByIp(String ip);

    Optional<SuspiciousIpModel> findIpByIp(String ip);

    List<SuspiciousIpModel> findAll();
}
