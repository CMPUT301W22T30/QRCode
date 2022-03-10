package com.example.qrcodeteam30;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.qrcodeteam30.modelclass.QRCode;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.spec.SecretKeySpec;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void sha256_test() throws NoSuchAlgorithmException {
        assertEquals(MyCryptography.hashSHA256("abc"),
                "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad");
        assertEquals(MyCryptography.hashSHA256("BFG5DGW54"),
                "8227ad036b504e39fe29393ce170908be2b1ea636554488fa86de5d9d6cd2c32");
        assertEquals(MyCryptography.hashSHA256("4710247000478"),
                "b4eca66af8bbd84ca8d647af762af30ff51c44255db8e7bc1e64164e9fcbc433");
    }


    @Test
    public void calculateScore_test1() {
        assertEquals(QRCode.calculateScore("BFG5DGW54"), 19.0, 0.00001);
        assertEquals(QRCode.calculateScore("4710247000478"), 44.0, 0.00001);
        assertEquals(QRCode.calculateScore("066721009159"), 22.0, 0.00001);
    }

//Haven't done unit test for encrypt and decrypt
    @Test
    public void encryptDecrypt_test() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String str = "Hello";
        String key = "aaa";
        assertEquals(MyCryptography.encrypt(str, key), "");
        assertEquals(MyCryptography.decrypt("", key);



    }


    @Test
    public void encryptDecrypt_test2() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String str = "HelloWorld";
        String key = "abc";
        assertEquals(MyCryptography.encrypt(str, key), "");
        assertEquals(MyCryptography.decrypt("", key);



    }

    @Test
    public void encryptDecrypt_test3() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String str = "TestString";
        String key = "TestKey";
        assertEquals(MyCryptography.encrypt(str, key), "");
        assertEquals(MyCryptography.decrypt("", key);



    }
}