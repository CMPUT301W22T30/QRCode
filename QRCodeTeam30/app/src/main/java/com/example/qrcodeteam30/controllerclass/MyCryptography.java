package com.example.qrcodeteam30.controllerclass;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Handling anything related to cryptography (hash, encrypt, decrypt)
 * TO DO: Implement an encrypt/decrypt algorithm for QR Code content (planned using AES-256)
 */
public class MyCryptography {
    /**
     * Hash a string to SHA-256 hex representation
     * @param input
     * @return SHA-256 hex representation of the input string
     * @throws NoSuchAlgorithmException
     */
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

    /**
     * Encrypt a string
     * @param strToEncrypt
     * @return encrypted string
     */
    public static String encrypt(String strToEncrypt) {
        return "AAA" + strToEncrypt;

    }

    /**
     * Decrypt a string, which is encrypted by the method encrypt
     * @param strToDecrypt
     * @return decrypted String
     */
    public static String decrypt(String strToDecrypt) {
        return strToDecrypt.substring(3);

    }
}
