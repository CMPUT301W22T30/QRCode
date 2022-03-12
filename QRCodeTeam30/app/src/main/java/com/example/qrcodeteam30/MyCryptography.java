package com.example.qrcodeteam30;
//package com.AES;
import android.os.Build;



import java.nio.charset.StandardCharsets;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    private static SecretKeySpec secretKey;
//    private static byte[] key;
//
//    public static void setKey(final String myKey) {
//        MessageDigest sha = null;
//        try {
//            key = myKey.getBytes("UTF-8");
//            sha = MessageDigest.getInstance("SHA-1");
//            key = sha.digest(key);
//            key = Arrays.copyOf(key, 16);
//            secretKey = new SecretKeySpec(key, "AES");
//        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public static String encrypt(final String strToEncrypt, final String secret) {
//        try {
//            setKey(secret);
//            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//            cipher.doFinal(strToEncrypt.getBytes("UTF-8"));
//        } catch (Exception e) {
//            System.out.println("Error while encrypting: " + e.toString());
//        }
//        return null;
//    }
//
//
//    public static String decrypt(final String strToDecrypt, final String secret) {
//        try {
//            setKey(secret);
//            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
//            cipher.init(Cipher.DECRYPT_MODE, secretKey);
//            return new String(cipher.doFinal(strToDecrypt.getBytes("UTF-8")));
//        } catch (Exception e) {
//            System.out.println("Error while decrypting: " + e.toString());
//        }
//        return null;
//    }

    public static String encrypt(String strToEncrypt) {
        return "AAA" + strToEncrypt;

    }

    public static String decrypt(String strToDecrypt) {
        return strToDecrypt.substring(3);


    }

}