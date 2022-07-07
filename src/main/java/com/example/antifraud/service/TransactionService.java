package com.example.antifraud.service;

import com.example.antifraud.DTO.IsIpV4;
import com.example.antifraud.DTO.LuhnAlgorithm;
import com.example.antifraud.model.TransactionModel;
import com.example.antifraud.repository.StolenCardRepository;
import com.example.antifraud.repository.SuspiciousIpRepository;
import com.example.antifraud.repository.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final StolenCardRepository stolenCardRepository;
    private final SuspiciousIpRepository suspiciousIpRepository;

    private final int MAX_AMOUNT_FOR_ALLOWED = 200;
    private final int MAX_AMOUNT_FOR_MANUAL_PROCESSING = 1500;

    public TransactionService(TransactionRepository antiFraudRepository,
                              StolenCardRepository stolenCardRepository,
                              SuspiciousIpRepository suspiciousIpRepository) {
        this.transactionRepository = antiFraudRepository;
        this.stolenCardRepository = stolenCardRepository;
        this.suspiciousIpRepository = suspiciousIpRepository;
    }

    public ResponseEntity<?> checkTransaction(TransactionModel deal) {
        if (deal.getIp() == null || deal.getNumber() == null || !checkCardValid(deal) || !checkIpValid(deal) || deal.getAmount() == null || deal.getAmount() < 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (stolenCardIsPresent(deal) || suspiciousIpIsPresent(deal) || deal.getAmount() > MAX_AMOUNT_FOR_MANUAL_PROCESSING) {
            TransactionModel deal1 = setValues(deal, "PROHIBITED");
            transactionRepository.save(deal1);
            return new ResponseEntity<>(getProhibitedInfo(deal1), HttpStatus.OK);
        } else if (deal.getAmount() > MAX_AMOUNT_FOR_ALLOWED) {
            TransactionModel deal1 = setValues(deal, "MANUAL_PROCESSING");
            transactionRepository.save(deal1);
            return new ResponseEntity<>(Map.of(
                    "result", deal1.getResponse(),
                    "info", "amount"), HttpStatus.OK);
        } else {
            TransactionModel deal1 = setValues(deal, "ALLOWED");
            transactionRepository.save(deal1);
            return new ResponseEntity<>(Map.of(
                    "result", deal1.getResponse(),
                    "info", "none"), HttpStatus.OK);
        }
    }

    public TransactionModel setValues(TransactionModel transaction, String response) {
        TransactionModel deal = new TransactionModel(transaction.getResponse(), transaction.getAmount());
        deal.setIp(transaction.getIp());
        deal.setNumber(transaction.getNumber());
        deal.setResponse(response);
        return deal;
    }

    public boolean checkIpValid(TransactionModel transaction) {
        return IsIpV4.validateIp(transaction.getIp());
    }

    public boolean checkCardValid(TransactionModel transaction) {
        return LuhnAlgorithm.validCard(transaction.getNumber());
    }

    public boolean suspiciousIpIsPresent(TransactionModel transaction) {
        return suspiciousIpRepository.findIpByIp(transaction.getIp()).isPresent();
    }

    public boolean stolenCardIsPresent(TransactionModel transaction) {
        return stolenCardRepository.findCardByNumber(transaction.getNumber()).isPresent();
    }

    public Map<String, String> getProhibitedInfo(TransactionModel transaction) {
        Map<String, String> mapOfInfo = new LinkedHashMap<>();
        List<String> listOfResult = new ArrayList<>();
        List<String> listOfInfo = new ArrayList<>();
        if (stolenCardIsPresent(transaction)) listOfInfo.add("card-number");
        if (suspiciousIpIsPresent(transaction)) listOfInfo.add("ip");
        if (transaction.getAmount() > MAX_AMOUNT_FOR_MANUAL_PROCESSING) listOfInfo.add("amount");
        Collections.sort(listOfInfo);
        listOfResult.add(transaction.getResponse());

        listOfResult.forEach(x -> mapOfInfo.put("result", x));
        for (String s : listOfInfo) {
            if (mapOfInfo.containsKey("info")) {
                mapOfInfo.put("info", mapOfInfo.get("info") + ", " + s);
            } else {
                mapOfInfo.put("info", s);
            }
        }
        System.out.println(mapOfInfo);

        return mapOfInfo;
    }

}
