package com.example.antifraud.DTO;

import com.example.antifraud.model.StolenCardModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class StolenCardDTO {
    private Long id;
    private String number;

    public StolenCardDTO(StolenCardModel stolenCard) {
        this.id = stolenCard.getId();
        this.number = stolenCard.getNumber();
    }
}
