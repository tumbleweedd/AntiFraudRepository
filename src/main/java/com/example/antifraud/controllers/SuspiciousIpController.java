package com.example.antifraud.controllers;

import com.example.antifraud.model.SuspiciousIpModel;
import com.example.antifraud.service.SuspiciousIpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SuspiciousIpController {
    private final SuspiciousIpService suspiciousIpService;

    public SuspiciousIpController(SuspiciousIpService suspiciousIpService) {
        this.suspiciousIpService = suspiciousIpService;
    }

    @PostMapping("/api/antifraud/suspicious-ip")
    public ResponseEntity<?> saveSuspiciousIp(@RequestBody SuspiciousIpModel suspiciousIpModel) {
        return suspiciousIpService.saveSuspiciousIp(suspiciousIpModel);
    }

    @DeleteMapping("/api/antifraud/suspicious-ip/{ip}")
    public ResponseEntity<?> deleteSuspiciousIp(@PathVariable("ip") String suspiciousIp) {
        return suspiciousIpService.deleteSuspiciousIp(suspiciousIp);
    }

    @GetMapping("/api/antifraud/suspicious-ip")
    public ResponseEntity<?> getSuspiciousIpList() {
        return suspiciousIpService.getIpList();
    }
}
