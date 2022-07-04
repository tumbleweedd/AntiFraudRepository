package com.example.antifraud.controllers;

import com.example.antifraud.model.StolenCardModel;
import com.example.antifraud.service.StolenCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StolenCardController {
    private final StolenCardService stolenCardService;

    StolenCardController(StolenCardService stolenCardService) {
        this.stolenCardService = stolenCardService;
    }

    @PostMapping("/api/antifraud/stolencard")
    public ResponseEntity<?> saveStolenCard(@RequestBody StolenCardModel stolenCard) {
        return stolenCardService.saveStolenCard(stolenCard);
    }

    @DeleteMapping("/api/antifraud/stolencard/{number}")
    public ResponseEntity<?> deleteStolenCard(@PathVariable("number") String stolenCard) {
        return stolenCardService.deleteStolenCard(stolenCard);
    }

    @GetMapping("/api/antifraud/stolencard")
    public ResponseEntity<?> getStolenCardList() {
        return stolenCardService.getListOfStolenCard();
    }
}
