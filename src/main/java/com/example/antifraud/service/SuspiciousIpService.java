package com.example.antifraud.service;

import com.example.antifraud.DTO.IsIpV4;
import com.example.antifraud.DTO.SuspiciousIpDTO;
import com.example.antifraud.model.SuspiciousIpModel;
import com.example.antifraud.repository.SuspiciousIpRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SuspiciousIpService {
    private final SuspiciousIpRepository suspiciousIpRepository;

    public SuspiciousIpService(SuspiciousIpRepository suspiciousIpRepository) {
        this.suspiciousIpRepository = suspiciousIpRepository;
    }

    public ResponseEntity<?> saveSuspiciousIp(SuspiciousIpModel suspiciousIp) {
        if (!IsIpV4.validateIp(suspiciousIp.getIp()) || suspiciousIp.getIp() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (suspiciousIpRepository.findIpByIp(suspiciousIp.getIp()).isPresent())
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        suspiciousIpRepository.save(suspiciousIp);
        return new ResponseEntity<>(new SuspiciousIpDTO(suspiciousIp), HttpStatus.CREATED);
    }

    public ResponseEntity<?> deleteSuspiciousIp(String ip) {
        if (suspiciousIpRepository.findIpByIp(ip).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!IsIpV4.validateIp(ip)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        suspiciousIpRepository.delete(suspiciousIpRepository.findIpByIp(ip).get());
        return new ResponseEntity<>(Map.of("status", "Ip " + ip + " successfully removed!"), HttpStatus.OK);
    }

    public ResponseEntity<?> getIpList() {
        List<SuspiciousIpModel> suspiciousIpModelList = suspiciousIpRepository.findAll();

        if (!suspiciousIpModelList.isEmpty())
            return new ResponseEntity<>(suspiciousIpRepository
                    .findAll()
                    .stream()
                    .map(SuspiciousIpDTO::new)
                    .collect(Collectors.toList()), HttpStatus.OK);
        return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.OK);
    }
}
