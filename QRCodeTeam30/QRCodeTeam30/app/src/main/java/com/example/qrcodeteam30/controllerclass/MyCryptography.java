package com.example.qrcodeteam30.controllerclass;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MyCryptography {
    static public String hashSHA256(String input) throws NoSuchAlgorithmException {
        try {
            final var digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            final var hexString = new StringBuilder();
            for (byte b : hash) {
                final var hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static String encrypt(String strToEncrypt) {
        return "AAA" + strToEncrypt;

    }

    public static String decrypt(String strToDecrypt) {
        return strToDecrypt.substring(3);

    }
}
