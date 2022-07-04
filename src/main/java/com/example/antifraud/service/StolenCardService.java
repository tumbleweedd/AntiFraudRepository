package com.example.antifraud.service;

import com.example.antifraud.DTO.LuhnAlgorithm;
import com.example.antifraud.DTO.StolenCardDTO;
import com.example.antifraud.model.StolenCardModel;
import com.example.antifraud.repository.StolenCardRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StolenCardService {
    private final StolenCardRepository stolenCardRepository;

    public StolenCardService(StolenCardRepository stolenCardRepository) {
        this.stolenCardRepository = stolenCardRepository;
    }

    public ResponseEntity<?> saveStolenCard(StolenCardModel stolenCard) {
        if (stolenCardRepository.findCardByNumber(stolenCard.getNumber()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        if (stolenCard.getNumber() == null || !LuhnAlgorithm.validCard(stolenCard.getNumber())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        stolenCardRepository.save(stolenCard);
        return new ResponseEntity<>(new StolenCardDTO(stolenCard), HttpStatus.OK);
    }

    public ResponseEntity<?> deleteStolenCard(String stolenCardNumber) {
        if (!LuhnAlgorithm.validCard(stolenCardNumber)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (stolenCardRepository.findCardByNumber(stolenCardNumber).isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        stolenCardRepository.delete(stolenCardRepository.findCardByNumber(stolenCardNumber).get());
        return new ResponseEntity<>(Map.of(
                "status",
                "Card " + stolenCardNumber + " successfully removed!"), HttpStatus.OK);
    }

    public ResponseEntity<?> getListOfStolenCard() {
        List<StolenCardModel> stolenCardList = stolenCardRepository.findAll();

        if (!stolenCardList.isEmpty())
            return new ResponseEntity<>(stolenCardList
                    .stream()
                    .map(StolenCardDTO::new)
                    .collect(Collectors.toList()),
                    HttpStatus.OK
            );

        return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.OK);
    }
}
