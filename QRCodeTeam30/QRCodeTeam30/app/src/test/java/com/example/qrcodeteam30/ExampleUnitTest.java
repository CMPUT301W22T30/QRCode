package com.example.qrcodeteam30;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.qrcodeteam30.controllerclass.CalculateScore;
import com.example.qrcodeteam30.controllerclass.MyCryptography;
import com.example.qrcodeteam30.modelclass.QRCode;

import java.security.NoSuchAlgorithmException;

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
    public void sha256Test() throws NoSuchAlgorithmException {
        assertEquals(MyCryptography.hashSHA256("abc"),
                "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad");
        assertEquals(MyCryptography.hashSHA256("BFG5DGW54"),
                "8227ad036b504e39fe29393ce170908be2b1ea636554488fa86de5d9d6cd2c32");
    }


    @Test
    public void calculateScoreTest() {
        double score = CalculateScore.calculateScore("BFG5DGW54");
        assertEquals(score, 19.0, 0.00001);
    }


    @Test
    public void encryptDecryptTest() {
        String str = "Hello";
        assertEquals(MyCryptography.encrypt(str), "AAAHello");
        assertEquals(MyCryptography.decrypt("AAAHello"), str);
    }
}