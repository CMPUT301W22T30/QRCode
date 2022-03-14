package com.example.qrcodeteam30;

import static org.junit.Assert.assertEquals;

import com.example.qrcodeteam30.controllerclass.CalculateScoreController;
import com.example.qrcodeteam30.controllerclass.MyCryptographyController;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;

public class MyCryptographyUnitTest {
    @Test
    public void sha256Test1() throws NoSuchAlgorithmException {
        assertEquals(MyCryptographyController.hashSHA256("abc"),
                "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad");
    }

    @Test
    public void sha256Test2() throws NoSuchAlgorithmException {
        assertEquals(MyCryptographyController.hashSHA256("a"),
                "ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb");
    }

    @Test
    public void sha256Test3() throws NoSuchAlgorithmException {
        assertEquals(MyCryptographyController.hashSHA256("A"),
                "559aead08264d5795d3909718cdd05abd49572e84fe55590eef31a88a08fdffd");
    }

    @Test
    public void sha256Test4() throws NoSuchAlgorithmException {
        assertEquals(MyCryptographyController.hashSHA256("BFG5DGW54"),
                "8227ad036b504e39fe29393ce170908be2b1ea636554488fa86de5d9d6cd2c32");
    }

    @Test
    public void sha256Test5() throws NoSuchAlgorithmException {
        assertEquals(MyCryptographyController.hashSHA256(""),
                "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
    }

    @Test
    public void sha256Test6() throws NoSuchAlgorithmException {
        assertEquals(MyCryptographyController.hashSHA256(" "),
                "36a9e7f1c95b82ffb99743e0c5c4ce95d83c9a430aac59f84ef3cbfab6145068");
    }

    @Test
    public void calculateScoreTest() {
        double score = CalculateScoreController.calculateScore("BFG5DGW54");
        assertEquals(score, 19.0, 0.00001);
    }


    @Test
    public void encryptDecryptTest1() {
        String str = "Hello";
        assertEquals(MyCryptographyController.encrypt(str), "AAAHello");
        assertEquals(MyCryptographyController.decrypt("AAAHello"), str);
    }

    @Test
    public void encryptDecryptTest2() {
        String str = "";
        assertEquals(MyCryptographyController.encrypt(str), "AAA");
        assertEquals(MyCryptographyController.decrypt("AAA"), str);
    }

    @Test
    public void encryptDecryptTest3() {
        String str = "AAA";
        assertEquals(MyCryptographyController.encrypt(str), "AAAAAA");
        assertEquals(MyCryptographyController.decrypt("AAAAAA"), str);
    }
}
