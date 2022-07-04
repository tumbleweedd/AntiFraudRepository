package com.example.antifraud.DTO;

import com.example.antifraud.model.SuspiciousIpModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SuspiciousIpDTO {
    private Long id;
    private String ip;

    public SuspiciousIpDTO(SuspiciousIpModel suspiciousIp) {
        this.id = suspiciousIp.getId();
        this.ip = suspiciousIp.getIp();
    }
}
