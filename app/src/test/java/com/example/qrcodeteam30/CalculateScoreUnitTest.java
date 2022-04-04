package com.example.qrcodeteam30;

import static org.junit.Assert.assertEquals;

import com.example.qrcodeteam30.controllerclass.CalculateScoreController;
import com.example.qrcodeteam30.modelclass.Game;
import com.example.qrcodeteam30.modelclass.QRCode;
import com.example.qrcodeteam30.modelclass.UserInformation;

import org.junit.Test;

import java.util.ArrayList;

public class CalculateScoreUnitTest {
    @Test
    public void calculateScoreTest() {
        double score = CalculateScoreController.calculateScore("BFG5DGW54");
        assertEquals(19.0, score, 0.00001);
    }

    @Test
    public void calculateScoreTest2() {
        double score = CalculateScoreController.calculateScore("");
        assertEquals(27.0, score, 0.00001);
    }

    @Test
    public void calculateScoreTest3() {
        double score = CalculateScoreController.calculateScore(" ");
        assertEquals(34.0, score, 0.00001);
    }

    @Test
    public void calculateScoreTest4() {
        double score = CalculateScoreController.calculateScore("a");
        assertEquals(59.0, score, 0.00001);
    }

    @Test
    public void calculateScoreTest5() {
        double score = CalculateScoreController.calculateScore("A");
        assertEquals(80.0, score, 0.00001);
    }

    @Test
    public void calculateScoreTest6() {
        double score = CalculateScoreController.calculateScore("abcd");
        assertEquals(17.0, score, 0.00001);
    }

    @Test
    public void calculateScoreTest7() {
        double score = CalculateScoreController.calculateScore("abcD");
        assertEquals(36.0, score, 0.00001);
    }

    @Test
    public void calculateTotalScoreInAGame1() {
        UserInformation userInformation = new UserInformation("admin", "placeHolder", "James", "Le");
        ArrayList<QRCode> arrayList = new ArrayList<>();
        QRCode qrCode = new QRCode();
        qrCode.setQrCodeContent("abcD");
        qrCode.setScore(36);
        qrCode.setGameName("adminName");
        qrCode.setGameOwner("admin");
        arrayList.add(qrCode);
        userInformation.setQrCodeList(arrayList);

        double score = CalculateScoreController.calculateTotalScore(userInformation, new Game("adminName", "admin"));
        assertEquals(score, 36, 0.00001);
    }

    @Test
    public void calculateTotalScoreInAGame2() {
        UserInformation userInformation = new UserInformation("admin", "placeHolder", "James", "Le");
        ArrayList<QRCode> arrayList = new ArrayList<>();

        QRCode qrCode1 = new QRCode();
        qrCode1.setQrCodeContent("abcD");
        qrCode1.setScore(36);
        qrCode1.setGameName("adminName");
        qrCode1.setGameOwner("admin");
        arrayList.add(qrCode1);

        QRCode qrCode2 = new QRCode();
        qrCode2.setQrCodeContent("abcD");
        qrCode2.setScore(36);
        qrCode2.setGameName("adminName2");
        qrCode2.setGameOwner("admin");
        arrayList.add(qrCode2);

        userInformation.setQrCodeList(arrayList);

        double score = CalculateScoreController.calculateTotalScore(userInformation, new Game("adminName", "admin"));
        assertEquals(score, 36, 0.00001);
    }

    @Test
    public void calculateTotalScoreInAGame3() {
        UserInformation userInformation = new UserInformation("admin", "placeHolder", "James", "Le");
        ArrayList<QRCode> arrayList = new ArrayList<>();

        QRCode qrCode1 = new QRCode();
        qrCode1.setQrCodeContent("abcD");
        qrCode1.setScore(36);
        qrCode1.setGameName("adminName");
        qrCode1.setGameOwner("admin");
        arrayList.add(qrCode1);

        QRCode qrCode2 = new QRCode();
        qrCode2.setQrCodeContent("abcD");
        qrCode2.setScore(36);
        qrCode2.setGameName("adminName");
        qrCode2.setGameOwner("admin");
        arrayList.add(qrCode2);

        userInformation.setQrCodeList(arrayList);

        double score = CalculateScoreController.calculateTotalScore(userInformation, new Game("adminName", "admin"));
        assertEquals(score, 72, 0.00001);
    }
}
