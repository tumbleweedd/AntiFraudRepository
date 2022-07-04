package com.example.antifraud.controllers;

import com.example.antifraud.model.TransactionModel;
import com.example.antifraud.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/antifraud")
public class TransactionController {
    private final TransactionService dealService;

    public TransactionController(TransactionService antiFraudService) {
        this.dealService = antiFraudService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<?> transaction(@RequestBody TransactionModel deal) {

        return dealService.checkTransaction(deal);
    }

}
