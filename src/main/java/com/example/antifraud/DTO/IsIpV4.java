package com.example.antifraud.DTO;

public class IsIpV4 {
    public static boolean validateIp(String ip) {
        try {
            if (ip == null || ip.isEmpty()) {
                return false;
            }

            String[] splitIp = ip.split("\\.");
            if (splitIp.length != 4) {
                return false;
            }

            for (String s : splitIp) {
                int i = Integer.parseInt(s);
                if (i < 0 || i > 255) {
                    return false;
                }
            }
            return !ip.endsWith(".");
        }catch (NumberFormatException formatException) {
            return false;
        }
    }
}
